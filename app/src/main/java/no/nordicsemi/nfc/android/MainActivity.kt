package no.nordicsemi.nfc.android

import android.app.Activity
import android.nfc.tech.*
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import no.nordicsemi.android.common.navigation.NavigationView
import no.nordicsemi.android.common.theme.NordicActivity
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.navigation.NfcNavigationDestinations
import no.nordicsemi.nfcscanner.repository.NfcScanningManagerVM
import java.util.*

@AndroidEntryPoint
class MainActivity : NordicActivity() {

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NordicTheme {
                val viewModel = hiltViewModel<NfcScanningManagerVM>()
                val activity = LocalContext.current as Activity
                DisposableEffect(key1 = viewModel) {
                    viewModel.onResume(activity)
                    onDispose { viewModel.onPause(activity) }
                }
                SelectionContainer {
                    NavigationView(destinations = NfcNavigationDestinations)
                }
            }
        }
    }
}