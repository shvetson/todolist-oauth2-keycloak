plugins {
    kotlin("jvm")
}

//group = rootProject.group
//version = rootProject.version

dependencies {
    val datetimeVersion: String by project
    val coroutinesVersion: String by project

    implementation(kotlin("stdlib-common"))

    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")

    implementation(project(":common"))
}