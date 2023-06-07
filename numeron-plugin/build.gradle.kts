plugins {
    kotlin("jvm")
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {
        implementation(project(":numeron-api"))
        implementation(project(":numeron-core"))
        implementation(project(":numeron-utils"))
        implementation(project(":numeron-menu"))
        implementation(project(":numeron-deps"))
    }
}