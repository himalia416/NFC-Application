plugins {
    alias(libs.plugins.nordic.library.compose)
    alias(libs.plugins.nordic.hilt)
}

android {
    namespace = "no.nordicsemi.nfc.navigation"
}

dependencies {
    implementation(libs.nordic.navigation)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(project(":nfc_scanner"))
    implementation(project(":nfc_ui"))
    implementation(project(":nfc_settings"))
    implementation(project(":nfc_welcome"))

    implementation(project(":nfc_ble"))
}