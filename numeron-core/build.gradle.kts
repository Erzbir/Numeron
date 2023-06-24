plugins {
    kotlin("jvm")
    id("java")
    `maven-publish`
}

dependencies {
    compileOnly(project(":numeron-api"))
    compileOnly(project(":numeron-utils"))
    compileOnly(project(":numeron-deps"))

    testImplementation(project(":numeron-api"))
    testImplementation(project(":numeron-utils"))
    testImplementation(project(":numeron-deps"))
}