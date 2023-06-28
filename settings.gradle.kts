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
            from("no.nordicsemi.android.gradle:version-catalog:1.5.10")
        }
    }
}

rootProject.name = "NFCApplication"
include(":app")
include(":nfcScanner")
include(":settings")
include(":welcome")
include(":navigation")
include(":serialization")
include(":domain")
include(":remoteDatabase")
include(":settingsStorage")
include(":nfcUi")
include(":handOverSelectMessageParser")

if (file("../Android-Common-Libraries").exists()) {
    includeBuild("../Android-Common-Libraries")
}
