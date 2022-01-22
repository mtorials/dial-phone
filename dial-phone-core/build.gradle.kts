plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

repositories {
    maven("https://maven.pkg.github.com/Dominaezzz/matrix-kt") {
        credentials {
            username = "mtorials"
            password = "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"
        }
    }
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
    js {

    }
    val ktorVersion: String by rootProject.extra
    val kotlinxCoroutinesVersion: String by rootProject.extra
    val kotlinxSerializationVersion: String by rootProject.extra
    sourceSets {
        val commonMain by getting {
            dependencies {

                // MODULES
                implementation(project(":dial-phone-api"))

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")
                //Ktor
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")

                implementation("io.github.matrixkt:olm:0.1.8")
            }
        }
    }
}
