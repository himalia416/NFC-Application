plugins {
    alias(libs.plugins.nordic.library.compose)
    alias(libs.plugins.nordic.hilt)
}

android {
    namespace = "no.nordicsemi.nfc.scanner"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.nordic.theme)
    implementation(libs.nordic.navigation)
    implementation(libs.nordic.permissions.nfc)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(project(":nfc_domain"))
    implementation(project(":nfc_settings_storage"))
    implementation(project(":nfc_welcome"))

    implementation(project(":nfc_ui"))
    implementation(project(":nfc_settings"))
    implementation(project(":nfc_utils"))
}