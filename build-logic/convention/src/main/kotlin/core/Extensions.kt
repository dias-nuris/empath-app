package core

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposePlugin

internal const val IMPLEMENTATION = "implementation"
internal const val API = "api"
internal const val DEBUG_IMPLEMENTATION = "debugImplementation"
internal const val TEST_IMPLEMENTATION = "testImplementation"

internal val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal val Project.androidLibs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("androidLibs")

internal val Project.compose
    get() = ComposePlugin.Dependencies(this)

internal fun DependencyHandlerScope.implementation(dependency: Any) =
    add(IMPLEMENTATION, dependency)

internal fun DependencyHandlerScope.api(dependency: Any) =
    add(API, dependency)

internal fun DependencyHandlerScope.debugImplementation(dependency: Any) =
    add(DEBUG_IMPLEMENTATION, dependency)

internal fun DependencyHandlerScope.testImplementation(dependency: Any) =
    add(TEST_IMPLEMENTATION, dependency)

