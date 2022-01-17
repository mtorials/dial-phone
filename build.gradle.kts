plugins {
    val kotlinVersion = "1.6.0"

    kotlin("multiplatform") version kotlinVersion apply false
    kotlin("plugin.serialization") version kotlinVersion apply false
    `maven-publish`
    id("org.jetbrains.dokka") version "0.10.0"
    //id("de.gesellix.docker") version "2021-12-18T23-58-00"
    id("com.avast.gradle.docker-compose") version "0.14.13"
}

group = "de.mtorials"
version = "v1.1.1-alpha"

allprojects {
    repositories {
        mavenCentral()
        jcenter()
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
}

subprojects {

}

extra.apply {
    set("ktorVersion", "1.6.2")
    set("kotlinxCoroutinesVersion", "1.4.2")
    set("kotlinxSerializationVersion", "1.0.1")
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
