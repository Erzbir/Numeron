import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "7.1.1"
}

dependencies {
    compileOnly(project(":numeron-api"))
    compileOnly(project(":numeron-console"))
    compileOnly(project(":numeron-deps"))
    implementation(project(":plugin-dep"))
}

tasks.withType<ShadowJar> {
    this.dependencies {
        exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.10"))
        exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib:1.7.10"))
        exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.7.10"))
        exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib-common:1.7.10"))
    }
}