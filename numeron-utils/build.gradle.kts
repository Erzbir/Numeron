plugins {
    kotlin("jvm")
    id("java")
    `maven-publish`
}

dependencies {
    implementation(project(":numeron-common"))
}