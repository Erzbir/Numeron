import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm")
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.1"
    id("application")
    `maven-publish`
}

val javaMainClass = "com.erzbir.numeron.NumeronBotApplication"

application {
    mainClass.set(javaMainClass)
}

dependencies {
    val unUseSubModule: List<String> = listOf(
        "numeron-plugin",
        "numeron-boot",
        "numeron-all",
        "plugin-test",
        "plugin-dep"
    )

    val unUsePluginSubModule: List<String> = listOf()
    val plugins = rootProject.subprojects.firstOrNull { it.name == "numeron-plugin" }?.subprojects
    val module = rootProject.subprojects.filter { plugins == null || plugins.contains(it).not() }

    fun importModule() {
        module.filter { unUseSubModule.contains(it.name).not() }.forEach {
            logger.error(it.name)
            implementation(it)
        }
    }

    fun importPlugins() {
        plugins?.filter { unUsePluginSubModule.contains(it.name).not() }?.forEach {
            logger.error(it.name)
            runtimeOnly(it)
        }
    }

    importModule()
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