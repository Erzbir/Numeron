plugins {
    kotlin("jvm")
    id("java")
    `maven-publish`
}

dependencies {
    compileOnly(project(":numeron-deps"))
}