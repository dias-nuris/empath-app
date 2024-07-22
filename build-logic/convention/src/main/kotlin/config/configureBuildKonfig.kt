package config

import core.Libs
import core.libs
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Buildkonfig fields setup in core:network [build.gradle.kts] file]
 * defaultConfigs can't be implemented here :(
 **/
internal fun Project.configureBuildKonfig(
    extension: KotlinMultiplatformExtension,
) = with(pluginManager) {
    apply(libs.findPlugin(Libs.BUILDKONFIG).get().get().pluginId)
}

object BuildKonfig {

    const val PACKAGE_NAME = "com.example.project"
    const val OBJECT_NAME = "BuildKonfig"
    const val EXPOSE_OBJECT_NAME = "BuildKonfig"

    object Key{
        const val BASE_URL = "BASE_URL"
    }

    object Release{
        const val NAME = "release"
        const val BASE_URL = "https://smartcity.curs.kz/api/v1/"
    }

    object Debug{
        const val NAME = "debug"
        const val BASE_URL = "https://smartcity-dev.curs.kz/api/v1/"
    }

}



