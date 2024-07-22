package plugin

import com.android.build.gradle.LibraryExtension
import config.configureAndroidLibrary
import config.configureIOSFramework
import config.*
import config.configureMultiplatform
import core.AndroidLibs
import core.Libs
import core.androidLibs
import core.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal class MultiplatformNetworkPlugin : Plugin<Project> {
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
                configureIOSFramework(this)
                configureKtor(this)
                configureKoin(this)
                configureBuildKonfig(this)
            }
        }
    }
}