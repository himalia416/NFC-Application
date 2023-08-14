plugins {
    alias(libs.plugins.nordic.library.compose)
    alias(libs.plugins.nordic.hilt)
}

android {
    namespace = "no.nordicsemi.nfc.serialization"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    // moshi
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.adapters)
    implementation(project(":nfc_domain"))

    implementation(project(":nfc_handover"))
}