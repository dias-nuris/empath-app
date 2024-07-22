package config

import com.android.build.api.dsl.CommonExtension
import core.Libs
import core.implementation
import core.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureKoin(
    extension: KotlinMultiplatformExtension,
) = with(extension) {
    sourceSets.apply {
        commonMain.dependencies {
            implementation(libs.findLibrary(Libs.KOIN_CORE).get())
            implementation(libs.findLibrary(Libs.KOIN_TEST).get())
        }
        iosMain.dependencies {
            implementation(libs.findLibrary(Libs.KOIN_CORE).get())
            implementation(libs.findLibrary(Libs.KOIN_TEST).get())
        }
    }
}

internal fun Project.configureKoin(
    extension: CommonExtension<*, *, *, *, *>,
) = extension.apply {
    dependencies {
        implementation(libs.findLibrary(Libs.KOIN_CORE).get())
        implementation(libs.findLibrary(Libs.KOIN_ANDROID).get())
    }
}