package config

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureMultiplatform(
    extension: KotlinMultiplatformExtension,
) = extension.apply {
    jvmToolchain(11)

    androidTarget()

    applyDefaultHierarchyTemplate()

    sourceSets.apply {
        androidMain.dependencies {}
        iosMain.dependencies {}
        commonMain.dependencies {}
        commonTest.dependencies {}
    }
}