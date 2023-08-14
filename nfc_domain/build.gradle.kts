plugins {
    alias(libs.plugins.nordic.library.compose)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.nordic.hilt)
}

android {
    namespace = "no.nordicsemi.nfc.domain"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(project(":nfc_handover"))
    implementation(project(":nfc_utils"))
}