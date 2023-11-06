import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("application")
}

repositories {
    mavenCentral()
}

val javaMainClass = "com.erzbir.numeron.NumeronBotApplication"

application {
    mainClass.set(javaMainClass)
}

val miraiVersion = ext["miraiVersion"]

dependencies {
    implementation("net.mamoe:mirai-core-jvm:${miraiVersion}")
    implementation(project(":numeron-console"))
    implementation(project(":numeron-utils"))
    implementation(project(":numeron-api"))
    implementation(project(":numeron-boot"))
    runtimeOnly(project(":numeron-core"))

    val unUsePluginSubModule: List<String> = listOf()
    val plugins = rootProject.subprojects.firstOrNull { it.name == "numeron-plugin" }?.subprojects

    fun importPlugins() {
        plugins?.filter { unUsePluginSubModule.contains(it.name).not() }?.forEach {
            logger.error(it.name)
            runtimeOnly(it)
        }
    }

    importPlugins()
}


tasks.withType<ShadowJar> {
    manifest {
        attributes["Main-Class"] = "com.erzbir.numeron.NumeronBotApplication"
        attributes["Multi-Release"] = "true"
        attributes["Application-Name"] = rootProject.name
        attributes["Mirai-Version"] = ext["miraiVersion"]
        attributes["Created-By"] = this@withType.name
    }
    mergeServiceFiles()
    archiveBaseName.set("numeron")
}
