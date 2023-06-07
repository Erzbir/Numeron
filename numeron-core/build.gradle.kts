plugins {
    kotlin("jvm")
}

dependencies {
    compileOnly(project(":numeron-api"))
    compileOnly(project(":numeron-utils"))

    compileOnly(project(":numeron-deps"))
}