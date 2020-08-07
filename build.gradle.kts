import org.gradle.jvm.tasks.Jar

plugins {
    kotlin("jvm") version "1.3.72"
    id("org.jetbrains.dokka") version "0.10.0"
    maven
    java
}

group = "de.mtorials"
version = "v0.1.1-alpha"

repositories {
    mavenCentral()
    jcenter()
}

val artifactID = "dial-phone"

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.+")
    implementation(group = "org.http4k", name = "http4k-core", version = "3.254.0")
    implementation(group = "org.http4k", name = "http4k-client-okhttp", version = "3.254.0")
}

tasks.dokka {
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"
}

val dokkaJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Documentation for DialPhone."
    from(tasks.dokka)
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

artifacts {
    archives(sourcesJar)
    archives(dokkaJar)
}

tasks.test {
    useJUnit()
}