plugins {
    alias(libs.plugins.nordic.library.compose)
    alias(libs.plugins.nordic.hilt)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "no.nordicsemi.bleconnection"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.nordic.navigation)
    implementation(libs.nordic.theme)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.nordic.blek.client)
    implementation(libs.nordic.permissions.ble)
    implementation(libs.nordic.blek.scanner)

    implementation(project(":settings"))
}