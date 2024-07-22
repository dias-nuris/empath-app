rootProject.name = "EmpathApp"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    includeBuild("build-logic")
}

dependencyResolutionManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    versionCatalogs {
        create("androidLibs") {
            from(files("gradle/androidLibs.versions.toml"))
        }
    }
}

include(":composeApp")

//android features
include(":features-android:root-navigation")
include(":features-android:auth")

//android core
include (":core-android:ui")

//shared features
include(":features-shared:root-navigation")
include(":features-shared:root-di")
include(":features-shared:auth-ui")
include(":features-shared:auth-api")

//shared core
include(":core-shared:network")
include(":core-shared:utils")
include(":core-shared:ui")