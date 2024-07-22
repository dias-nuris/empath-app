package app

import org.gradle.api.JavaVersion
import org.gradle.api.Project

data class AndroidApp(
    val namespace: String = "kaiyrzhan.de.android",
    val javaVersion: JavaVersion = JavaVersion.VERSION_11,
)

internal val androidApp by lazy { AndroidApp() }

internal fun Project.createNamespace(): String {
    val namespace = "${androidApp.namespace}.${this.name.defineNamespace()}"
    println("${project.name} namespace -> $namespace")
    return namespace
}

private fun String.defineNamespace() = this.split('-')
    .mapIndexed { index, word ->
        if (index == 0) word else word.replaceFirstChar { char -> char.uppercase() }
    }
    .joinToString("")



