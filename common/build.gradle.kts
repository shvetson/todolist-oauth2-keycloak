plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

val datetimeVersion: String by project
val ktorVersion: String by project
val konformVersion: String by project

dependencies {
    implementation(kotlin("stdlib-common"))
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.konform:konform-jvm:$konformVersion")

    api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
}