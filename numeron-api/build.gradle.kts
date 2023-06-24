plugins {
    kotlin("jvm")
    id("java")
    `maven-publish`

}

dependencies {
    implementation(project(":numeron-deps"))
    api(project(":numeron-annotation"))
    implementation(project(":numeron-utils"))
}