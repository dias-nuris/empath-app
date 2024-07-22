package app

import org.gradle.api.Project

internal const val SHARED = "Shared"

internal fun Project.createSharedName(): String {
    val sharedName = "$SHARED${this.name.defineSharedName()}"
    println("${project.name} sharedName -> $sharedName")
    return sharedName
}

private fun String.defineSharedName() = this.split('-')
    .joinToString("") { word ->
        word.replaceFirstChar { char ->
            char.uppercase()
        }
    }