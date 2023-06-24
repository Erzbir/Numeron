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

    val javaVersion = JavaVersion.VERSION_19

    var jvmArgsList = listOf("--enable-preview")

    repositories {
        mavenLocal()

        mavenCentral()
        gradlePluginPortal()
        google()
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs = jvmArgsList

        java {
            sourceCompatibility = javaVersion
            targetCompatibility = javaVersion
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "17"
        }
    }

    tasks.withType<JavaExec> {
        jvmArgs = jvmArgsList
    }


    tasks.withType<Wrapper> {
        gradleVersion = "8.0"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        jvmArgs = jvmArgsList
    }
}