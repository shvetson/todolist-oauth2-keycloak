plugins {
    kotlin("jvm")
}

dependencies {
    val exposedVersion: String by project
    val postgresVersion: String by project
    val kmpUUIDVersion: String by project

    implementation(kotlin("stdlib"))
    implementation("com.benasher44:uuid:$kmpUUIDVersion")

    implementation("org.postgresql:postgresql:$postgresVersion")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

    implementation(project(":common"))
}