val kotlinVersion: String by project
val ktorVersion: String by project
val ktorPluginVersion: String by project
val logbackVersion: String by project

val exposedVersion: String by project
val hikariVersion: String by project
val postgresVersion: String by project

val commonsCodecVersion: String by project
val konformVersion: String by project

plugins {
    id("application")
    kotlin("jvm")
    id("io.ktor.plugin")
    kotlin("plugin.serialization")
}

group = "ru.shvets"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
    google()
    mavenCentral()
    maven { url = uri("https://kotlin.bintray.com/ktor") }
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktorVersion")

    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

    implementation("org.postgresql:postgresql:$postgresVersion")
    implementation("com.zaxxer:HikariCP:$hikariVersion")

    implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktorVersion")
    implementation("commons-codec:commons-codec:$commonsCodecVersion")

    implementation(kotlin("stdlib"))
    implementation("io.konform:konform-jvm:$konformVersion")

}