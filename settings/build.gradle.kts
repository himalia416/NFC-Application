plugins {
    alias(libs.plugins.nordic.library.compose)
    alias(libs.plugins.nordic.hilt)
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.setting"
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

    // Datastore
    implementation(libs.androidx.dataStore.preferences)
}