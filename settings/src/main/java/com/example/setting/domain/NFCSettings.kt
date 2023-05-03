package com.example.setting.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NFCSettings(
    val playSound: Boolean = false,
    val vibration: Boolean = false,
    val showWelcomeScreen: Boolean = true,
//    val uploadScanResult: Boolean = false,
    val showHistory: Boolean = false,
    val importScanResult: Boolean = false
) : Parcelable