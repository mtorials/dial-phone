plugins {
    kotlin("multiplatform") version "1.4.21"
    kotlin("plugin.serialization") version "1.4.10"
}

group = "de.mtorials"
version = "v0.1.1-alpha"


repositories {
    mavenCentral()
    jcenter()
}

kotlin {

    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
                implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.+")
                implementation("org.http4k:http4k-core:3.254.0")
                implementation("org.http4k:http4k-client-okhttp:3.254.0")
            }
        }
    }
}

val artifactID = "dial-phone"