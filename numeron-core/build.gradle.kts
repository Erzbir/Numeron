plugins {
    kotlin("jvm")
}

dependencies {
    compileOnly(project(":numeron-api"))
    compileOnly(project(":numeron-utils"))
    compileOnly(project(":numeron-deps"))

    testImplementation(project(":numeron-api"))
    testImplementation(project(":numeron-utils"))
    testImplementation(project(":numeron-deps"))
}