plugins {
    kotlin("jvm")
}

dependencies {
    compileOnly(project(":numeron-api"))
    compileOnly(project(":numeron-core"))
    compileOnly(project(":numeron-utils"))
}