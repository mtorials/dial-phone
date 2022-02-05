pluginManagement {
    repositories {
        //maven { url 'https://dl.bintray.com/kotlin/kotlin-eap' }
        mavenCentral()
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
}
rootProject.name = "dial-phone"

include(
    "dial-phone-api",
    "dial-phone-core",
    "dial-phone-encryption",
    "dial-phone-bot",
    "dial-phone-test"
)

