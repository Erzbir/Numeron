plugins {
    kotlin("jvm")
    id("java")
    `maven-publish`
}

dependencies {
    compileOnly(project(":numeron-api"))
    compileOnly(project(":numeron-core"))
    compileOnly(project(":numeron-utils"))
    compileOnly(project(":numeron-deps"))
}