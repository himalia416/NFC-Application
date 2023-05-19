plugins {
    alias(libs.plugins.nordic.library.compose)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.example.domain"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)
}