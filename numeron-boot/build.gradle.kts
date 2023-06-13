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
    implementation(project(":numeron-deps"))
    implementation(project(":numeron-console"))

    implementation(project(":numeron-plugin:card-search"))
    implementation(project(":numeron-plugin:chat"))
    implementation(project(":numeron-plugin:check-weight"))
    implementation(project(":numeron-plugin:code-process"))
    implementation(project(":numeron-plugin:help"))
    implementation(project(":numeron-plugin:openai"))
    implementation(project(":numeron-plugin:plugin-control"))
    implementation(project(":numeron-plugin:qq-manage"))
    implementation(project(":numeron-plugin:rss"))
    implementation(project(":numeron-plugin:sign"))
    implementation(project(":numeron-plugin:switch"))
}

tasks.withType<ShadowJar>() {
    manifest {
        attributes["Main-Class"] = "com.erzbir.numeron.NumeronBotApplication"
        attributes["Multi-Release"] = "true"
    }
    archiveBaseName.set("numeron")
}