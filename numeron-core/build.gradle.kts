plugins {
    kotlin("jvm")
    id("java")
    `maven-publish`
}

dependencies {
    compileOnly(project(":numeron-api"))
    compileOnly(project(":numeron-utils"))
    compileOnly(project(":numeron-deps"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.0")
}