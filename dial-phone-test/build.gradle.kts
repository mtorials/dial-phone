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
    val kotlinxCoroutinesVersion: String by rootProject.extra
    val kotlinxSerializationVersion: String by rootProject.extra
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

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")

                implementation("ch.qos.logback:logback-core:$logbackVersion")
                implementation("ch.qos.logback:logback-classic:$logbackVersion")
            }
        }
    }
}

// DOCKER

dockerCompose {
    dockerComposeWorkingDirectory.set(rootDir)
    useComposeFiles.add("test-compose.yaml")
    captureContainersOutput.set(false)
    waitForTcpPorts.set(true)
    checkContainersRunning.set(true)
    removeContainers.set(true)
}

//val createUserOnHomeserver = tasks.creating(Exec::class) {
//    // TODO windows support
//
//}

// TESTING

tasks.withType<Test> {
    dockerCompose.isRequiredBy(this)
    doFirst {
        project.exec {
            commandLine("docker", "exec",
                "synapse-test",
                "register_new_matrix_user",
                "-u", "test",
                "-p", "test",
                "--no-admin",
                "-c", "/config/homeserver.yaml",
                "http://localhost:8008"
            )
        }
    }
//    dependsOn(createUserOnHomeserver)
    useJUnit()
//    testLogging {
//        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
//        events = mutableSetOf(org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED, org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED, org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED)
//        showStandardStreams = true
//    }
}
