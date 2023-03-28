package com.example.nfcapplication.views

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nfcapplication.viewmodel.*

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun NfcScreen() {
        val context = LocalContext.current
        val nfcViewModel: NfcViewModel = hiltViewModel()
        val nfcState by nfcViewModel.state.collectAsState()
        val sn = nfcState.nfcScanningState.serialNumber
        val allAvailableTechnology = nfcState.nfcScanningState.tagTechnology

        when (nfcState.state) {
            NfcScanTag -> ScanNfcTagView()
            NfcNotEnabled -> ShowEnableNfcView(
                onSettingClicked = { context.startActivity(Intent(Settings.ACTION_NFC_SETTINGS)) },
                onCancelClicked = { nfcViewModel.enableNfc() }
            )
            NfcNotSupported -> ShowNfcNotSupportedView()
            NfcTagDiscovered -> ShowTagInformationView(
                serialNumber = sn,
                allAvailableTechnology = allAvailableTechnology,
                onBackButtonClicked = { nfcViewModel.showScanTag() }
            )
            EnableNfc -> ShowNfcNotEnableView()
        }
}