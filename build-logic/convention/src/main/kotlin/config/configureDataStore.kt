package config

import core.Libs
import core.libs
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureDataStore(
    extension: KotlinMultiplatformExtension,
) = with(extension) {
    sourceSets.commonMain.dependencies {
        api(libs.findLibrary(Libs.DATASTORE).get())
        api(libs.findLibrary(Libs.DATASTORE_PREFERENCES).get())
    }
}