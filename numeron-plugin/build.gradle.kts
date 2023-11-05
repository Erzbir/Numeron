plugins {
    kotlin("jvm")
    id("java")
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "java")
    dependencies {
        compileOnly(project(":numeron-api"))
        compileOnly(project(":numeron-utils"))
        compileOnly(project(":numeron-menu"))
        compileOnly(project(":numeron-common"))
        compileOnly(project(":numeron-console"))
    }
}