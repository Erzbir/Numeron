plugins {
    kotlin("jvm")
}

dependencies {
//  implem
    implementation(project(":numeron-deps"))
    api(project(":numeron-annotation"))
    implementation(project(":numeron-utils"))
}