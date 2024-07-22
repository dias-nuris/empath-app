package config

import com.android.build.api.dsl.CommonExtension
import core.AndroidLibs
import core.androidLibs
import core.compose
import core.debugImplementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureCompose(
    extension: KotlinMultiplatformExtension,
) = with(extension) {
    with(sourceSets) {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(androidLibs.findLibrary(AndroidLibs.COMPOSE_ACTIVITY).get())
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }
    }
}

internal fun Project.configureCompose(
    extensions: CommonExtension<*, *, *, *, *>,
) = with(extensions) {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
    buildFeatures.compose = true
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}