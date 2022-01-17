import java.time.*


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

subprojects {

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
        mavenLocal()
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
            artifactId = project.name
            version = "1.1"
        }
    }
}
