package config

import com.android.build.api.dsl.CommonExtension
import core.Libs
import core.implementation
import core.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework

internal fun Project.configureDecompose(
    extension: KotlinMultiplatformExtension,
) = extension.apply {
    with(pluginManager) {
        apply(libs.findPlugin(Libs.KOTLINX_SERIALIZATION).get().get().pluginId)
    }
    sourceSets.commonMain.dependencies {
        api(libs.findLibrary(Libs.DECOMPOSE).get())
        api(libs.findLibrary(Libs.ESSENTY_LIFECYCLE).get())
        implementation(libs.findLibrary(Libs.KOTLINX_SERIALIZATION_JSON).get())
        implementation(libs.findLibrary(Libs.DECOMPOSE_COMPOSE).get())
    }
}

internal fun Project.configureDecompose(
    framework: Framework,
) = framework.apply {
    export(libs.findLibrary(Libs.DECOMPOSE).get())
    export(libs.findLibrary(Libs.ESSENTY_LIFECYCLE).get())
}

internal fun Project.configureDecompose(
    extension: CommonExtension<*, *, *, *, *>,
) = extension.apply {
    dependencies {
        implementation(libs.findLibrary(Libs.DECOMPOSE_COMPOSE).get())
    }
}