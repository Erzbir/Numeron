plugins {

}

dependencies {
    api(project(":numeron-api"))
    api(project(":numeron-utils"))
    runtimeOnly("org.xerial:sqlite-jdbc:3.42.0.0")
}