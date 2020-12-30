plugins {
    kotlin("multiplatform") version "1.4.21"
    kotlin("plugin.serialization") version "1.4.10"
    //kotlin("plugin.coroutines") version "1.4.10"
}

group = "de.mtorials"
version = "v0.1.1-alpha"


repositories {
    mavenCentral()
    jcenter()
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
    js(LEGACY) {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
    }
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
                implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.+")
                implementation("org.http4k:http4k-core:3.254.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
                //implementation("org.http4k:http4k-client-okhttp:3.254.0")
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                //Ktor
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")

            }

        }
        val commonTest by getting {

        }
    }
}

val artifactID = "dial-phone"