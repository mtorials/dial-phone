import org.gradle.jvm.tasks.Jar

plugins {
    kotlin("jvm") version "1.3.72"
    id("org.jetbrains.dokka") version "0.10.0"
    `maven-publish`
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

val dokkaJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles Kotlin docs with Dokka"
    from(tasks.dokka)
}

publishing {
    publications {
        //create<MavenPublication>("default") {
        //    from(components["java"])
        //    artifact(dokkaJar)
        //}
        create<MavenPublication>("gpr") {
            run {
                artifact(dokkaJar)
            }
        }
    }
    repositories {
        maven {
            url = uri("$buildDir/repository")
        }
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/mtorials/dial-phone")
            credentials {
                username = System.getenv("GH_ACTOR") ?: File("username").readText()
                password = System.getenv("GH_TOKEN") ?: File("ghtoken").readText()
            }
        }
    }
}