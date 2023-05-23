plugins {
    kotlin("jvm")
}

val coroutinesVersion: String by project
val konformVersion: String by project

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("stdlib-common"))
    implementation("io.konform:konform-jvm:$konformVersion")

    implementation(project(":common"))
    implementation(project(":lib-cor"))
}