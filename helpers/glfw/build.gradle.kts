plugins {
    id("java-library")
    id("maven-publish")
}

dependencies {
    implementation(project(":core"))

    api(libs.lwjgl.glfw)
    implementation(variantOf(libs.lwjgl.glfw.natives) { classifier("natives-windows") })
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "net.flamgop.vulkanic"
            artifactId = "helpers-glfw"
            version = "0.0.1"
        }
    }
}