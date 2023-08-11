plugins {
    kotlin("jvm")
    id("java")
    `maven-publish`

}

dependencies {
    api(project(":numeron-deps"))
    api(project(":numeron-utils"))
}