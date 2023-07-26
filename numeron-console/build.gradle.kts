plugins {
    kotlin("jvm")
    id("java")
}

dependencies {
    compileOnly(project(":numeron-api"))
    compileOnly(project(":numeron-utils"))
    compileOnly(project(":numeron-menu"))
    compileOnly(project(":numeron-deps"))
    compileOnly(project(":numeron-core"))
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}