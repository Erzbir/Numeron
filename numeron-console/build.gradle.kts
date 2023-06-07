plugins {
    kotlin("jvm")
}

dependencies {
    compileOnly(project(":numeron-core"))
    compileOnly(project(":numeron-api"))
    compileOnly(project(":numeron-utils"))
    compileOnly(project(":numeron-menu"))
    compileOnly(project(":numeron-deps"))
}

