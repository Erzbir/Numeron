buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}

plugins {
    kotlin("jvm") version "1.7.10"
    id("java")
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "java")
}

allprojects {
    group = "com.erzbir.numeron"

    version = "1.0.0"

    val javaVersion = JavaVersion.VERSION_17
    val miraiVersion = "2.15.0-RC"
    val gradleVersion = "8.0"
    val encoding = "UTF-8"

    ext {
        set("javaVersion", javaVersion)
        set("miraiVersion", miraiVersion)
        set("gradleVersion", "8.0")
        set("encoding", "UTF-8")
    }

    repositories {
        mavenLocal()

        mavenCentral()
        gradlePluginPortal()
        google()
    }

    tasks.withType<JavaCompile> {
        options.encoding = encoding

        java {
            sourceCompatibility = javaVersion
            targetCompatibility = javaVersion
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = javaVersion.toString()
        }
    }

    tasks.withType<JavaExec> {
    }


    tasks.withType<Wrapper> {
        this.gradleVersion = gradleVersion
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}