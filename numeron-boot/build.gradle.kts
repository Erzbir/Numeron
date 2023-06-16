import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "7.1.1"
}

dependencies {
    val unUseSubModule: List<String> = listOf(
        "numeron-plugin",
        "numeron-boot"
    )
    val unUsePluginSubModule: List<String> = listOf()

    fun importModule() {
        rootProject.subprojects.filter { unUseSubModule.contains(it.name).not() }.forEach {
            implementation(it)
        }
    }

    fun importPlugins() {
        rootProject.subprojects.firstOrNull { it.name == "numeron-plugin" }?.let { project ->
            project.subprojects.filter { unUsePluginSubModule.contains(it.name).not() }
                .forEach {
                    runtimeOnly(it)
                }
        }
    }

    importModule()
    importPlugins()
}


tasks.withType<ShadowJar>() {
    manifest {
        attributes["Main-Class"] = "com.erzbir.numeron.NumeronBotApplication"
        attributes["Multi-Release"] = "true"
    }
    mergeServiceFiles()
    archiveBaseName.set("numeron")
}