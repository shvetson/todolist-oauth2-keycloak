rootProject.name = "todolist"

pluginManagement {
    val kotlinVersion: String by settings
//    val openapiVersion: String by settings
    val ktorPluginVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
//        id("org.openapi.generator") version openapiVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("io.ktor.plugin") version ktorPluginVersion apply false
    }
}