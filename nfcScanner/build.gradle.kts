plugins {
    alias(libs.plugins.nordic.library.compose)
    alias(libs.plugins.nordic.hilt)
}

android {
    namespace = "no.nordicsemi.nfcscanner"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.nordic.theme)
    implementation(libs.nordic.navigation)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.bom)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.nordic.core)

    implementation(project(":domain"))
    implementation(project(":remoteDatabase"))
    implementation(project(":settingsStorage"))
    implementation(project(":welcome"))
    implementation(project(":nfcUi"))

    // Balloon for tooltip popup.
    implementation ("com.github.skydoves:balloon-compose:1.5.2")
}