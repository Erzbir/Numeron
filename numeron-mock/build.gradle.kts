plugins {
    kotlin("jvm")
    id("java")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(project(":numeron-console"))
    implementation(project(":numeron-deps"))
    implementation(project(":numeron-annotation"))
    implementation(project(":numeron-api"))
    implementation(project(":numeron-utils"))

}