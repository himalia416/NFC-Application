plugins {
    alias(libs.plugins.nordic.application.compose)
    alias(libs.plugins.nordic.hilt)
    alias(libs.plugins.google.services)
//    id ("com.google.gms.google-services") //Google services Gradle plugin
}

android {
    namespace = "com.example.nfcapplication"
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

    implementation ("com.google.firebase:firebase-database-ktx:20.2.0")
    implementation ("com.google.firebase:firebase-analytics-ktx:21.2.2")
}