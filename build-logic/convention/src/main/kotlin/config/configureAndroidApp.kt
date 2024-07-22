package config

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import app.androidApp
import core.AndroidLibs
import core.androidLibs
import org.gradle.api.Project

internal fun Project.configureAndroidApp(
    extension: BaseAppModuleExtension,
) = extension.apply {
    compileSdk = androidLibs.findVersion(AndroidLibs.COMPILE_SDK).get().requiredVersion.toInt()
    namespace = androidApp.namespace
    defaultConfig.apply {
        applicationId = androidApp.namespace
        minSdk = androidLibs.findVersion(AndroidLibs.MIN_SDK).get().requiredVersion.toInt()
        targetSdk =
            androidLibs.findVersion(AndroidLibs.TARGET_SDK).get().requiredVersion.toInt()
        versionCode =
            androidLibs.findVersion(AndroidLibs.VERSION_CODE).get().requiredVersion.toInt()
        versionName = androidLibs.findVersion(AndroidLibs.VERSION_NAME).get().requiredVersion
    }
    packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    compileOptions {
        sourceCompatibility = androidApp.javaVersion
        targetCompatibility = androidApp.javaVersion
    }
}
