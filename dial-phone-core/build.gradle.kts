plugins {
    kotlin("multiplatform")
    id("com.avast.gradle.docker-compose")
}

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

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")
                //Ktor
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
            }
        }
    }
}

// DOCKER

val synapseImageId = "matrixdotorg/synapse:latest"

dockerCompose {
    useComposeFiles.add("test-compose.yaml")
    waitForTcpPorts.set(true)
    checkContainersRunning.set(true)
    removeContainers.set(true)
}

// TESTING


tasks.withType<Test> {
    useJUnit()
    dockerCompose.isRequiredBy(this)
//    testLogging {
//        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
//        events = mutableSetOf(org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED, org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED, org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED)
//        showStandardStreams = true
//    }
}
