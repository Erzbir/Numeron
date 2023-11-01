pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}
rootProject.name = "Numeron"


include(
    listOf(
        ":numeron-api",
        ":numeron-deps",
        ":numeron-boot",
        ":numeron-console",
        ":numeron-core",
        ":numeron-menu",
        ":numeron-plugin",
        ":numeron-utils",
        ":numeron-mock",
        ":numeron-all",
        ":numeron-plugin:card-search",
        ":numeron-plugin:chat",
        ":numeron-plugin:check-weight",
        ":numeron-plugin:code-process",
        ":numeron-plugin:help",
        ":numeron-plugin:openai",
        ":numeron-plugin:plugin-control",
        ":numeron-plugin:qq-manage",
        ":numeron-plugin:rss",
        ":numeron-plugin:sign",
        ":numeron-plugin:switch",
        ":numeron-plugin:mcbot",
        ":plugin-dep",
        ":plugin-test"
    )
)