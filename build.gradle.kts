plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.kapt) apply true
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.nordic.application) apply false
    alias(libs.plugins.nordic.application.compose) apply false
    alias(libs.plugins.nordic.library) apply false
    alias(libs.plugins.nordic.library.compose) apply false
    alias(libs.plugins.nordic.hilt) apply false
    alias(libs.plugins.nordic.feature) apply false
    alias(libs.plugins.google.services) apply true
    id("com.android.library") version "7.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
}
