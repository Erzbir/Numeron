buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}

plugins {
    kotlin("jvm") version "1.9.22"
    id("java")
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.1.1"
}


group = "com.erzbir"
version = "1.0.0"
val javaVersion = JavaVersion.VERSION_21
val gradleVersion = "8.5"
val encoding = "UTF-8"


subprojects {

    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "maven-publish")
    apply(plugin = "com.github.johnrengelman.shadow")

    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.30")
        compileOnly("org.slf4j:slf4j-api:2.0.12")

        implementation("ch.qos.logback:logback-classic:1.5.0")

        runtimeOnly("ch.qos.logback:logback-core:1.5.0")

        testImplementation(platform("org.junit:junit-bom:5.10.2"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testCompileOnly("org.slf4j:slf4j-api:2.0.12")
        testCompileOnly("org.projectlombok:lombok:1.18.30")
        testAnnotationProcessor("org.projectlombok:lombok:1.18.30")

        annotationProcessor("org.projectlombok:lombok:1.18.30")
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
        debugOptions.enabled
    }
}