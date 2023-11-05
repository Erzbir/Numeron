plugins {
    kotlin("jvm")
    id("java")
    `maven-publish`
}

dependencies {
    api(project(":numeron-api"))
    api(project(":numeron-utils"))
    api(project(":numeron-common"))
}