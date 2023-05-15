package com.example.welcome

import com.example.welcome.views.NfcWelcomeScreen
import no.nordicsemi.android.common.navigation.createSimpleDestination
import no.nordicsemi.android.common.navigation.defineDestination

val NfcWelcomeDestinationId = createSimpleDestination("nfc-welcome-screen")

val NfcWelcomeDestination = defineDestination(NfcWelcomeDestinationId) {
    NfcWelcomeScreen()
}