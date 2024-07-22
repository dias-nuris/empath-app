package config

import core.Libs
import core.libs
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureDateTime(
    extension: KotlinMultiplatformExtension,
) = with(extension) {
    sourceSets.commonMain.dependencies {
        implementation(libs.findLibrary(Libs.KOTLINX_DATETIME).get())
    }
}