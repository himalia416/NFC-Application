plugins {
    alias(libs.plugins.nordic.application.compose)
    alias(libs.plugins.nordic.hilt)
    alias(libs.plugins.google.services)
}

android {
    namespace = "no.nordicsemi.nfc.android"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.nordic.theme)
    implementation(libs.nordic.navigation)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(project(":navigation"))
    implementation(project(":nfcScanner"))
}