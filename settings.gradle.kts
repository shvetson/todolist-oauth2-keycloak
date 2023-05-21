rootProject.name = "todolist"

pluginManagement {
    val kotlinVersion: String by settings
    val ktorPluginVersion: String by settings

    // open api
    val openapiVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        id("org.openapi.generator") version openapiVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("io.ktor.plugin") version ktorPluginVersion apply false
    }
}

include("common")
include("api")
include("app")
include("mapper")