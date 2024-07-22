package config

import app.createSharedName
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework

internal fun Project.configureIOSFramework(
    extension: KotlinMultiplatformExtension,
    targetBinariesFramework: (Framework.() -> Unit)? = null,
) = with(extension) {
    val sharedName = project.createSharedName()
    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = sharedName
            targetBinariesFramework?.let {
                targetBinariesFramework()
            }
        }
    }
}