package plugin

import com.android.build.gradle.LibraryExtension
import config.*
import core.androidLibs
import core.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal class AndroidPresentationPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(androidLibs.findPlugin(core.AndroidLibs.ANDROID_LIBRARY).get().get().pluginId)
            apply(androidLibs.findPlugin(core.Libs.JETBRAINS_COMPOSE).get().get().pluginId)
            apply(libs.findPlugin(core.Libs.COMPOSE_COMPILER).get().get().pluginId)
            apply(libs.findPlugin(core.Libs.KOTLIN_MULTIPLATFORM).get().get().pluginId)
        }
        with(extensions) {
            configure<LibraryExtension> {
                configureAndroidLibrary(this)
                configureKoin(this)
                configureCompose(this)
                configureDecompose(this)
            }

            configure<KotlinMultiplatformExtension> {
                androidTarget {
                    @OptIn(ExperimentalKotlinGradlePluginApi::class)
                    compilerOptions {
                        jvmTarget.set(JvmTarget.JVM_11)
                    }
                }
                configureCompose(this)
            }
        }
    }
}