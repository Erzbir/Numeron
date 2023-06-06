import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
  kotlin("jvm")
  id("com.github.johnrengelman.shadow") version "7.1.1"
}

dependencies {
  implementation(project(":numeron-core"))
  implementation(project(":numeron-api"))
  implementation(project(":numeron-utils"))
  implementation(project(":numeron-menu"))
  implementation(project(":numeron-boot"))
  implementation(project(":numeron-deps"))
}

tasks.withType<ShadowJar>() {
  manifest {
    attributes["Main-Class"] = "com.erzbir.numeron.console.NumeronBotApplication"
  }
}