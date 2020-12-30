plugins {
    kotlin("multiplatform") version "1.4.21"
    //kotlin("plugin.serialization") version "1.4.10"
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

        }
    }

    dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.+")
        implementation(group = "org.http4k", name = "http4k-core", version = "3.254.0")
        implementation(group = "org.http4k", name = "http4k-client-okhttp", version = "3.254.0")

        testCompileOnly("io.kotlintest:kotlintest-core:3.0.2")
        testCompileOnly("io.kotlintest:kotlintest-assertions:3.0.2")
        testCompileOnly("io.kotlintest:kotlintest-runner-junit5:3.0.2")
    }
}
val artifactID = "dial-phone"
