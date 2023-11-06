plugins {

}

subprojects {

    dependencies {
        compileOnly(project(":numeron-api"))
        compileOnly(project(":numeron-utils"))
        compileOnly(project(":numeron-menu"))
        compileOnly(project(":numeron-console"))
        runtimeOnly("org.xerial:sqlite-jdbc:3.42.0.0")
        implementation("com.squareup.okhttp3:okhttp:4.10.0")
    }
}