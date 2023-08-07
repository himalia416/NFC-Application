plugins {
    alias(libs.plugins.nordic.library.compose)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.nordic.hilt)
}
android {
    namespace = "no.nordicsemi.nfcui"
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
    implementation(libs.nordic.blek.client)

    implementation(project(":domain"))
    implementation(project(":settings"))
    implementation(project(":handOverData"))
    implementation(project(":utils"))
    implementation(project(":remoteDatabase"))
}