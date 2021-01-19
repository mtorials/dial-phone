import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform") version "1.4.21"
    kotlin("plugin.serialization") version "1.4.10"
}

group = "de.mtorials"
version = "v1.0.0-alpha"


repositories {
    mavenCentral()
    jcenter()
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
}

val ktorVersion = "1.5.0"

kotlin {

    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")

                //Ktor
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")

                // Tests
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }

        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                //implementation("org.eclipse.jetty:jetty-client:11.0.0")
                implementation("io.ktor:ktor-client-cio:$ktorVersion")

            }
        }
    }
}

val artifactID = "dial-phone"
