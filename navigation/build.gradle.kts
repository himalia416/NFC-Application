plugins {
    alias(libs.plugins.nordic.library.compose)
    alias(libs.plugins.nordic.hilt)
}

android {
    namespace = "no.nordicsemi.navigation"
}

dependencies {
    implementation(libs.nordic.navigation)
    implementation(libs.androidx.compose.bom)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(project(":nfcScanner"))
    implementation(project(":nfcUi"))
    implementation(project(":settings"))
    implementation(project(":welcome"))
}