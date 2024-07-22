package plugin

import com.android.build.gradle.LibraryExtension
import config.*
import core.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal class MultiplatformPresentationPlugin : Plugin<Project> {
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
                configureDecompose(this)
                configureCoroutines(this)
            }
        }
    }
}