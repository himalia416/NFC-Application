package no.nordicsemi.nfcapplication

import android.nfc.tech.*
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import dagger.hilt.android.AndroidEntryPoint
import no.nordicsemi.android.common.navigation.NavigationView
import no.nordicsemi.android.common.theme.NordicActivity
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.navigation.NfcDestinations
import no.nordicsemi.profile_nfc.repository.NfcScanningManager
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : NordicActivity() {
    @Inject
    lateinit var nfcManager: NfcScanningManager

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(nfcManager)

        setContent {
            NordicTheme {
                SelectionContainer {
                    NavigationView(destinations = NfcDestinations)
                }
            }
        }
    }
}