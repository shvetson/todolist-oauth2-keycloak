plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":api"))
    implementation(project(":common"))

    testImplementation(kotlin("test-junit"))
}