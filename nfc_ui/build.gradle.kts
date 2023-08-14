plugins {
    alias(libs.plugins.nordic.library.compose)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.nordic.hilt)
}
android {
    namespace = "no.nordicsemi.nfc.ui"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.nordic.theme)
    implementation(libs.nordic.navigation)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.nordic.core)
    // Balloon for tooltip popup.
    implementation(libs.skydoves.ballon)

    implementation(project(":nfc_domain"))
    implementation(project(":nfc_settings"))
    implementation(project(":nfc_handover"))
    implementation(project(":nfc_utils"))
    implementation(project(":nfc_remote_database"))
    implementation(project(":nfc_ble"))
}