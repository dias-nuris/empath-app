plugins {
    id("android.app")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.featuresAndroid.rootNavigation)
            implementation(projects.featuresShared.rootDi)
            implementation(projects.coreShared.utils)
        }
    }
}

