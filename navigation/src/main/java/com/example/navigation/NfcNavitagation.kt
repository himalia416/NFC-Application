package com.example.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.profile_nfc.ScanningDestination
import com.example.setting.NfcSettingsDestination
import com.example.welcome.NfcWelcomeDestination

@RequiresApi(Build.VERSION_CODES.S)
val NfcDestinations = NfcWelcomeDestination + ScanningDestination + NfcSettingsDestination