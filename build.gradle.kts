buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}

plugins {
    kotlin("jvm") version "1.7.10" apply false
}

allprojects {
    group = "com.erzbir.numeron"

    repositories {
        mavenLocal()

        mavenCentral()
        gradlePluginPortal()
        google()
    }

    tasks.withType<JavaCompile>() {
        options.encoding = "UTF-8"
    }
}