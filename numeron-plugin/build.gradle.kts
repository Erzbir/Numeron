plugins {
    kotlin("jvm")
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {
        compileOnly(project(":numeron-api"))
        compileOnly(project(":numeron-utils"))
        compileOnly(project(":numeron-menu"))
        compileOnly(project(":numeron-deps"))
        compileOnly(project(":numeron-console"))
    }
}