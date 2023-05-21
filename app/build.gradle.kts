val kotlinVersion: String by project
val ktorVersion: String by project
val ktorPluginVersion: String by project
val logbackVersion: String by project

val exposedVersion: String by project
val hikariVersion: String by project
val postgresVersion: String by project

val commonsCodecVersion: String by project

val konformVersion: String by project

val kotestVersion: String by project
val jUnitJupiterVersion: String by project

//fun ktorServer(module: String, version: String? = this@Build_gradle.ktorVersion): Any =
//    "io.ktor:ktor-server-$module:$version"
//fun ktorClient(module: String, version: String? = this@Build_gradle.ktorVersion): Any =
//    "io.ktor:ktor-client-$module:$version"

plugins {
    id("application")
    kotlin("jvm")
    id("io.ktor.plugin")
    kotlin("plugin.serialization")
}

repositories {
    maven {
        url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
    }  // подключение репозитория с ktor плагинами
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
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

    implementation(project(":common"))

    testImplementation(kotlin("test-junit5"))
    testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$jUnitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$jUnitJupiterVersion")
}

tasks {
    withType<Test>().configureEach {
        //useJUnitPlatform необходимо для работы с junit5, в случае работы с junit (по умолчанию версия 4) эти настройки не нужны
        useJUnitPlatform {
//            includeTags.add("sampling")
        }

        filter {
            isFailOnNoMatchingTests = false
        }
        testLogging {
            showExceptions = true
            showStandardStreams = true
            events = setOf(org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED, org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED)
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        }
    }
}