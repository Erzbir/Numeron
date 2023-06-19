plugins {
    kotlin("jvm")
}

dependencies {
    compileOnly(project(":numeron-api"))
    compileOnly(project(":numeron-utils"))
    compileOnly(project(":numeron-menu"))
    compileOnly(project(":numeron-deps"))
    testImplementation(project(":numeron-api"))
    testImplementation(project(":numeron-utils"))
    testImplementation(project(":numeron-menu"))
    testImplementation(project(":numeron-deps"))
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}