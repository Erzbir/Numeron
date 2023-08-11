plugins {
    kotlin("jvm")
    id("java")
    `maven-publish`

}

dependencies {
    api(project(":numeron-deps"))
    api(project(":numeron-utils"))
    api(project(":numeron-boot"))
    runtimeOnly(project(":numeron-core"))
    implementation(project(":numeron-api"))
}