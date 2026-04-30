rootProject.name = "vulkanic"

include("core")
include("helpers:glfw")
include("helpers:sdl")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("./libs.versions.toml"))
        }
    }
}