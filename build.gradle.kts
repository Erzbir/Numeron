import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

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
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.1.1"
}

rootProject.version = "1.0.0"
val javaVersion = JavaVersion.VERSION_17
val miraiVersion = "2.15.0"
val gradleVersion = "8.1.1"
val encoding = "UTF-8"

subprojects {

    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "maven-publish")
    apply(plugin = "com.github.johnrengelman.shadow")

    dependencies {
        annotationProcessor("org.projectlombok:lombok:1.18.30")
        compileOnly("org.projectlombok:lombok:1.18.30")
    }
}

allprojects {
    group = "com.erzbir.numeron"

    version = rootProject.version

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

    tasks.withType<Jar> {
        enabled
    }


    tasks.withType<Wrapper> {
        this.gradleVersion = gradleVersion
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

tasks.withType<ShadowJar> {
    mergeServiceFiles()
    minimize()
}