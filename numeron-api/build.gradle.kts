plugins {
    kotlin("jvm")
    id("java")
    `maven-publish`

}

dependencies {
    api(project(":numeron-deps"))
    api(project(":numeron-annotation"))
    api(project(":numeron-utils"))
}