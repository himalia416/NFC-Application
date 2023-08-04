plugins {
    alias(libs.plugins.nordic.library.compose)
    alias(libs.plugins.nordic.hilt)
}

android {
    namespace = "no.nordicsemi.nfcscanner"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.nordic.theme)
    implementation(libs.nordic.navigation)
    implementation(libs.nordic.permissions.nfc)
    implementation(libs.nordic.permissions.ble)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(project(":domain"))
    implementation(project(":settingsStorage"))
    implementation(project(":welcome"))
    implementation(project(":nfcUi"))
    implementation(project(":settings"))
    implementation(project(":utils"))
}