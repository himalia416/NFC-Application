plugins {
    alias(libs.plugins.nordic.library.compose)
    alias(libs.plugins.nordic.hilt)
}

android {
    namespace = "com.example.navigation"
}

dependencies {
    implementation(libs.nordic.navigation)
    implementation(libs.androidx.compose.bom)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(project(":profile_nfc"))
    implementation(project(":settings"))
    implementation(project(":welcome"))
}