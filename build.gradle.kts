plugins {
    kotlin("jvm") version "1.3.72"
    id("org.jetbrains.dokka") version "0.10.0"
    `maven-publish` apply true
}

group = "de.mtorials"
version = "0.1.0"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.+")
    implementation("com.github.kittinunf.fuel:fuel:2.2.2")
    implementation("com.github.kittinunf.fuel:fuel-coroutines:2.2.2")
    implementation("org.requirementsascode:moonwlker:0.0.6")
    testImplementation("junit:junit:4.12")
}

tasks.dokka {
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"
}

publishing {
    repositories {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/mtorials/dial-phone")
                credentials {
                    username = project.findProperty("gpr.user") as String? ?: System.getenv("GH_ACTOR")
                    password = project.findProperty("gpr.key") as String? ?: System.getenv("GH_TOKEN")
                }
            }
        }
    }
}