plugins {
    kotlin("jvm")
    id("java")
    `maven-publish`

}

dependencies {
    api(project(":numeron-common"))
    api(project(":numeron-utils"))
}