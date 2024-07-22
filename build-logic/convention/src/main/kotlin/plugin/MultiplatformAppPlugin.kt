package plugin

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import config.*
import core.*

internal class MultiplatformDataPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.findPlugin(Libs.KOTLIN_MULTIPLATFORM).get().get().pluginId)
            apply(androidLibs.findPlugin(AndroidLibs.ANDROID_LIBRARY).get().get().pluginId)
        }
        with(extensions) {
            configure<LibraryExtension> {
                configureAndroidLibrary(this)
            }
            configure<KotlinMultiplatformExtension> {
                configureMultiplatform(this)
                configureIOSFramework(
                    extension = this,
                    targetBinariesFramework = {
                        configureDecompose(this)
                    }
                )
                configureKoin(this)
                configureKtor(this)
                configureCoroutines(this)
                configureDateTime(this)
            }
        }
    }
}