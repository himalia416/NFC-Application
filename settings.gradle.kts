pluginManagement {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://jitpack.io")
    }
    versionCatalogs {
        create("libs") {
            from("no.nordicsemi.android.gradle:version-catalog:1.9.0")
        }
    }
}

rootProject.name = "NFC Connect"
include(":app")
include(":nfc_scanner")
include(":nfc_settings")
include(":nfc_welcome")
include(":nfc_navigation")
include(":nfc_serialization")
include(":nfc_domain")
include(":nfc_remote_database")
include(":nfc_settings_storage")
include(":nfc_ui")
include(":nfc_handover")
include(":nfc_utils")
include(":nfc_ble")

if (file("../Android-Common-Libraries").exists()) {
    includeBuild("../Android-Common-Libraries")
}
