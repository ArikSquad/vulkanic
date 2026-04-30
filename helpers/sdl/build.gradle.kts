plugins {
    id("java-library")
    id("maven-publish")
}

dependencies {
    implementation(project(":core"))

    api(libs.lwjgl.sdl)
    implementation(variantOf(libs.lwjgl.sdl.natives) { classifier("natives-windows") })
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "net.flamgop.vulkanic"
            artifactId = "helpers-sdl"
            version = "0.0.1"
        }
    }
}