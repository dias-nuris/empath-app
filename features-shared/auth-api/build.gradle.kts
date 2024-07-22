plugins{
    id("multiplatform.data")
}

kotlin{
    sourceSets.commonMain.dependencies {
        implementation(projects.coreShared.network)
    }
}