plugins {
    alias(libs.plugins.nordic.library.compose)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.nordic.hilt)
}

android {
    namespace = "no.nordicsemi.nfc.utils"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
}