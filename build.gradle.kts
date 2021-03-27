plugins {
    kotlin("multiplatform") version "1.4.31"
    kotlin("plugin.serialization") version "1.4.31"
    `maven-publish`
    id("org.jetbrains.dokka") version "0.10.0"
}

group = "de.mtorials"
version = "v1.1.2-alpha"


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

    js {
        binaries.executable()
        browser()
        nodejs()
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
        val jsTest by getting {
            dependencies {
                //implementation(kotlin("test-js"))
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
            artifactId = name
            version = "1.1"
        }
    }
}