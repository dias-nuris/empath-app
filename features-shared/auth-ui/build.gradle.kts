plugins{
    id("multiplatform.presentation")
}

kotlin{
    sourceSets.commonMain.dependencies {
        implementation(projects.featuresShared.authApi)
    }
}