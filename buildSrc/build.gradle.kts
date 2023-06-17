plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

val testcontainersVersion: String by project

dependencies {
    implementation("gradle.plugin.org.flywaydb:gradle-plugin-publishing:9.19.4")
    implementation("nu.studer:gradle-jooq-plugin:6.0.1")
    implementation("org.testcontainers:postgresql:$testcontainersVersion")
}
