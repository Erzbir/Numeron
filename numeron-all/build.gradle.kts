plugins {
    kotlin("jvm")
    id("java")
}

dependencies {
    api(project(":numeron-common"))
    api(project(":numeron-utils"))
    api(project(":numeron-boot"))
    runtimeOnly(project(":numeron-core"))
    api(project(":numeron-api"))
}