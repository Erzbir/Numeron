plugins {
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    val unUseSubModule: List<String> = listOf(
        "numeron-mock",
    )

    fun implementationImportModule() {
        rootProject.subprojects.filter { unUseSubModule.contains(it.name).not() }.forEach {
            implementation(it)
        }
    }

    implementationImportModule()
}
