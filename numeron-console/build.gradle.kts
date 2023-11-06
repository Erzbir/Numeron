plugins {

}

dependencies {
    api(project(":numeron-api"))
    api(project(":numeron-utils"))
    api(project(":numeron-boot"))
    implementation(project(":numeron-core"))
    api("commons-io:commons-io:2.13.0")
}