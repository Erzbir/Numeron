plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":numeron-deps"))
    testApi("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3")
}