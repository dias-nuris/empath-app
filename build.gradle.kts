plugins {
    alias(androidLibs.plugins.android.application) apply(false)
    alias(androidLibs.plugins.android.library) apply(false)
    alias(androidLibs.plugins.jetbrains.compose) apply(false)
    alias(libs.plugins.compose.compiler) apply(false)
    alias(libs.plugins.kotlin.multiplatform) apply(false)
    alias(libs.plugins.kotlin.android) apply(false)
    alias(libs.plugins.kotlinx.serialization) apply(false)
    alias(libs.plugins.ksp) apply(false)
}