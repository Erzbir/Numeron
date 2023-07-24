import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.1"
}

dependencies {
    compileOnly(project(":numeron-api"))
    compileOnly(project(":numeron-console"))
    compileOnly(project(":numeron-deps"))
    implementation(project(":plugin-dep"))
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.10")
}

tasks.withType<ShadowJar> {
}