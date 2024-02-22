import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("application")
    id("org.graalvm.buildtools.native") version "0.10.1"
}

dependencies {
    implementation(project(":numeron-api"))
    implementation(project(":numeron-core"))
}

val javaMainClass = "com.erzbir.numeron.Main"

application {
    mainClass.set(javaMainClass)
}


tasks.withType<ShadowJar> {
    manifest {
        attributes["Main-Class"] = javaMainClass
        attributes["Multi-Release"] = "true"
        attributes["Application-Name"] = rootProject.name
        attributes["Created-By"] = this@withType.name
    }
    mergeServiceFiles()
    archiveBaseName.set("numeron")
}

graalvmNative {
    binaries {
        named("main") {
            imageName.set(rootProject.name)
            mainClass.set(javaMainClass)
            useFatJar.set(true)
        }
    }
    binaries.all {
        buildArgs.add("--verbose")
    }
}


tasks.test {
    useJUnitPlatform()
}