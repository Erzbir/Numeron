plugins {
    kotlin("jvm")
    id("java")
}

dependencies {
    api(project(":numeron-api"))
    implementation(project(":numeron-utils"))
    implementation(project(":numeron-menu"))
    api(project(":numeron-common"))
    implementation(project(":numeron-core"))
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}