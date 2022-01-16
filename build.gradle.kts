//import com.bmuschko.gradle.docker.tasks.container.*
import java.time.*
//import de.gesellix.gradle.docker.tasks.*

plugins {
    val kotlinVersion = "1.6.10"
    kotlin("multiplatform") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    `maven-publish`
    id("org.jetbrains.dokka") version "0.10.0"
    //id("de.gesellix.docker") version "2021-12-18T23-58-00"
    id("com.avast.gradle.docker-compose") version "0.14.13"
}

group = "de.mtorials"
version = "v1.1.1-alpha"


repositories {
    mavenCentral()
    jcenter()
    //maven("https://dl.bintray.com/kotlin/kotlin-eap")
}

val ktorVersion = "1.6.2"
val logbackVersion = "1.3.0-alpha12"

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
                implementation("io.ktor:ktor-client-logging:$ktorVersion")

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
                implementation("org.eclipse.jetty:jetty-client:11.0.0")
                implementation("io.ktor:ktor-client-cio:$ktorVersion")

                implementation("ch.qos.logback:logback-core:$logbackVersion")
                implementation("ch.qos.logback:logback-classic:$logbackVersion")

                // testcontainers
//                val junitJupiterVersion = "5.4.2"
//                val testcontainerVersion = "1.16.2"
//                implementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
//                implementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
//                implementation("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
//                implementation("org.testcontainers:postgresql:$testcontainerVersion")
//                implementation("org.testcontainers:testcontainers:$testcontainerVersion")
//                implementation("org.testcontainers:junit-jupiter:$testcontainerVersion")
            }
        }
    }
}

val name = "dial-phone"

tasks.dokka {
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"
}

val dokkaJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Documentation for DialPhone."
    from(tasks.dokka)
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

repositories {
    maven {
        url = uri("https://gitlab.example.com/api/v4/groups/mtorials/-/packages/maven")
        name = "GitLab"
        credentials(HttpHeaderCredentials::class) {
            name = "Job-Token"
            value = System.getenv("CI_JOB_TOKEN")
        }
        authentication {
            create<HttpHeaderAuthentication>("header")
        }
    }
}


publishing {
    repositories {
        maven {
            url = uri("https://git.mt32.net/api/v4/projects/59/packages/maven")
            name = "GitLab"
            credentials(HttpHeaderCredentials::class) {
                name = "Job-Token"
                value = System.getenv("CI_JOB_TOKEN")
            }
            authentication {
                create<HttpHeaderAuthentication>("header")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "de.mtorials"
            artifactId = name
            version = "1.1"
        }
    }
}
