package config

import core.Libs
import core.libs
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureKtor(
    extension: KotlinMultiplatformExtension,
) = with(extension) {
    with(pluginManager) {
        apply(libs.findPlugin(Libs.KSP).get().get().pluginId)
        apply(libs.findPlugin(Libs.KOTLINX_SERIALIZATION).get().get().pluginId)
        apply(libs.findPlugin(Libs.KTORFIT).get().get().pluginId)
    }
    sourceSets.commonMain.dependencies {
        implementation(libs.findLibrary(Libs.KTORFIT).get())
        implementation(libs.findLibrary(Libs.KTOR_CORE).get())
        implementation(libs.findLibrary(Libs.KTOR_LOGGING).get())
        implementation(libs.findLibrary(Libs.KTOR_AUTH).get())
        implementation(libs.findLibrary(Libs.KTOR_NEGOTIATION).get())
        implementation(libs.findLibrary(Libs.KTOR_SERIALIZATION).get())
        implementation(libs.findLibrary(Libs.KTOR_SERIALIZATION_JSON).get())
        implementation(libs.findLibrary(Libs.KOTLINX_SERIALIZATION_JSON).get())
    }
}