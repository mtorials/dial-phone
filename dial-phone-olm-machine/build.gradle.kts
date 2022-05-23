import de.undercouch.gradle.tasks.download.Download

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
        val jvmMain by getting {
            dependencies {

                implementation("net.java.dev.jna:jna:5.7.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

val sdkVersion = "0.5.1"

val sdkRoot = projectDir.resolve("sdk/$sdkVersion")
val zipFile = sdkRoot.resolve("sdk.zip")
val buildDir = sdkRoot.resolve("build")

val downloadSdk by tasks.registering(Download::class) {
    src("https://github.com/matrix-org/matrix-rust-sdk/archive/refs/tags/matrix-sdk-base-$sdkVersion.zip")
    dest(zipFile)
}

val unzipSdk by tasks.registering(Copy::class) {
    from(zipTree(zipFile)) {
        include("*/**")
        eachFile {
            relativePath = RelativePath(true, *relativePath.segments.drop(1).toTypedArray())
        }
    }
    into(buildDir)
    dependsOn(downloadSdk)
}

val buildSdk by tasks.registering(Exec::class) {
    workingDir(buildDir)
    commandLine("cargo", "build",  "--release", "-p",  "matrix-sdk-crypto-ffi")
    dependsOn(unzipSdk)
}

val copySdk by tasks.registering(Copy::class) {
    from(buildDir.resolve("target/release/libmatrix_crypto.so"))
    into(projectDir.resolve("src/jvmMain/resources/"))
    rename("libmatrix_crypto.so", "libuniffi_olm.so")
    dependsOn(buildSdk)
}