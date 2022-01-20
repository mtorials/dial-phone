plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.avast.gradle.docker-compose")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
    val logbackVersion = "1.3.0-alpha12"
    val ktorVersion: String by rootProject.extra
    sourceSets {
        val jvmTest by getting {
            dependencies {
                // MODULES
                implementation(project(":dial-phone-core"))
                implementation(project(":dial-phone-api"))

                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(kotlin("test-junit"))
                //implementation("org.eclipse.jetty:jetty-client:11.0.0")
                implementation("io.ktor:ktor-client-cio:$ktorVersion")

                implementation("ch.qos.logback:logback-core:$logbackVersion")
                implementation("ch.qos.logback:logback-classic:$logbackVersion")
            }
        }
    }
}

// DOCKER

val synapseImageId = "matrixdotorg/synapse:latest"

dockerCompose {
    dockerComposeWorkingDirectory.set(rootDir)
    useComposeFiles.add("test-compose.yaml")
    captureContainersOutput.set(true)
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
