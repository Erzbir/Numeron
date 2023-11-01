buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}

plugins {
    kotlin("jvm") version "1.8.10"
    id("java")
}

val javaVersion = JavaVersion.VERSION_17
val miraiVersion = "2.15.0"
val gradleVersion = "8.1.1"
val encoding = "UTF-8"

subprojects {

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "java")

    dependencies {
        annotationProcessor("org.projectlombok:lombok:1.18.30")
    }
}

allprojects {
    group = "com.erzbir.numeron"

    version = "1.0.0"

    repositories {
        mavenLocal()

        mavenCentral()
        gradlePluginPortal()
        google()
    }

    ext {
        set("javaVersion", javaVersion)
        set("miraiVersion", miraiVersion)
        set("gradleVersion", gradleVersion)
        set("encoding", encoding)
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
        workingDir = rootDir
    }


    tasks.withType<Wrapper> {
        this.gradleVersion = gradleVersion
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}