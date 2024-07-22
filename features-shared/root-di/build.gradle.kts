plugins{
    id("multiplatform.presentation")
}

kotlin{
    sourceSets{
        commonMain.dependencies {
            implementation(projects.coreShared.utils)
            implementation(projects.coreShared.network)
            implementation(projects.featuresShared.authApi)
        }
    }
}
