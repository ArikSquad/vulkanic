import com.google.auto.service.AutoService;
import com.palantir.javapoet.*;
import net.flamgop.vulkanic.annotations.VulkanExtension;
import net.flamgop.vulkanic.annotations.VulkanFeature;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"net.flamgop.vulkanic.annotations.VulkanFeature", "net.flamgop.vulkanic.annotations.VulkanExtension"})
@SupportedSourceVersion(SourceVersion.RELEASE_25)
public class VulkanicFeatureProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty() || roundEnv.processingOver()) {
            return false;
        }

        ClassName targetClass = ClassName.get("net.flamgop.vulkanic.core.feature", "VulkanicDeviceFeatures");

        TypeSpec.Builder baseClass = TypeSpec.classBuilder("VulkanicFeaturesGenerated")
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);

        baseClass.addMethod(MethodSpec.methodBuilder("getChainedPNext")
                .addModifiers(Modifier.PROTECTED, Modifier.ABSTRACT)
                .addTypeVariable(com.palantir.javapoet.TypeVariableName.get("T", ClassName.get("org.lwjgl.system", "NativeResource")))
                .returns(com.palantir.javapoet.TypeVariableName.get("T"))
                .addParameter(ParameterizedTypeName.get(ClassName.get(Class.class), com.palantir.javapoet.TypeVariableName.get("T")), "type")
                .build());

        ParameterizedTypeName setType = ParameterizedTypeName.get(Set.class, String.class);
        baseClass.addField(FieldSpec.builder(setType, "extensions")
                .addModifiers(Modifier.PROTECTED, Modifier.FINAL)
                .initializer("new $T<>()", HashSet.class)
                .build());

        for (Element element : roundEnv.getElementsAnnotatedWith(VulkanFeature.class)) {
            processVulkanFeature(element, baseClass, targetClass);
        }

        for (Element element : roundEnv.getElementsAnnotatedWith(VulkanExtension.class)) {
            processVulkanExtension(element, baseClass, targetClass);
        }

        try {
            JavaFile.builder("net.flamgop.vulkanic.core.feature", baseClass.build())
                    .build()
                    .writeTo(processingEnv.getFiler());

            System.out.println("Wrote " + processingEnv.getFiler());
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(javax.tools.Diagnostic.Kind.ERROR, "Failed to write: " + e.getMessage());
        }

        return true;
    }

    private void processVulkanFeature(Element element, TypeSpec.Builder baseClass, ClassName targetClass) {
        String name = element.getSimpleName().toString();
        baseClass.addField(FieldSpec.builder(boolean.class, name)
                .addModifiers(Modifier.PROTECTED)
                .initializer("false")
                .build());

        VulkanFeature anno = element.getAnnotation(VulkanFeature.class);
        TypeName structType = getStructTypeName(element);

        MethodSpec.Builder enabler = MethodSpec.methodBuilder(name)
                .addModifiers(Modifier.PUBLIC)
                .returns(targetClass)
                .addStatement("this.$L = true", name);

        if (!anno.extension().isEmpty()) {
            enabler.addStatement("this.extensions.add($L)", "\"" + anno.extension() + "\"");
        }

        if (structType.toString().equals("org.lwjgl.vulkan.VkPhysicalDeviceFeatures2")) {
            enabler.addStatement("$L features = getChainedPNext($L.class)", structType, structType);
            if (!anno.setter().equals("$$SPECIAL_IGNORE$$")) enabler.addStatement("features.features().$L(true)", name);
            enabler.addStatement("return ($T)this", targetClass);
        } else {
            enabler.addStatement("$L features = getChainedPNext($L.class)", structType, structType);
            if (!anno.setter().equals("$$SPECIAL_IGNORE$$")) enabler.addStatement("features.$L(true)", name);
            enabler.addStatement("return ($T)this", targetClass);
        }

        baseClass.addMethod(enabler.build());

        MethodSpec getter = MethodSpec.methodBuilder("supports" + capitalize(name))
                .addModifiers(Modifier.PUBLIC)
                .returns(boolean.class)
                .addStatement("return this.$L", name)
                .build();

        baseClass.addMethod(getter);
    }

    private void processVulkanExtension(Element element, TypeSpec.Builder baseClass, ClassName targetClass) {
        String name = element.getSimpleName().toString();
        VulkanExtension anno = element.getAnnotation(VulkanExtension.class);

        MethodSpec.Builder enabler = MethodSpec.methodBuilder(name)
                .addModifiers(Modifier.PUBLIC)
                .returns(targetClass)
                .addStatement("this.extensions.add($S)", anno.value())
                .addStatement("return ($T)this", targetClass);

        baseClass.addMethod(enabler.build());
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private TypeName getStructTypeName(Element e) {
        try {
            return TypeName.get(e.getAnnotation(VulkanFeature.class).featuresStruct());
        } catch (MirroredTypeException mte) {
            return TypeName.get(mte.getTypeMirror());
        }
    }
}
