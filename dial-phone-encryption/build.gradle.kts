plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

val trixnityVersion = "2.0.0"

fun trixnity(module: String, version: String = trixnityVersion) =
    "net.folivo:trixnity-$module:$version"


kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
    val ktorVersion: String by rootProject.extra
    val kotlinxCoroutinesVersion: String by rootProject.extra
    val kotlinxSerializationVersion: String by rootProject.extra
    sourceSets {
        val commonMain by getting {
            dependencies {

                // MODULES
                implementation(project(":dial-phone-api"))
                implementation(project(":dial-phone-core"))

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")
                //Ktor
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")

//                implementation("io.github.matrixkt:olm:0.1.8")
                implementation(trixnity("olm"))


            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("net.java.dev.jna:jna:5.7.0")
            }
        }
    }
}



