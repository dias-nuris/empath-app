plugins {
    id("android.presentation")
}

android {
    dependencies {
        api(projects.featuresShared.rootNavigation)
    }
}