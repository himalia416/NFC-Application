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
import com.example.nfcapplication.repository.MifareClassicTag
import com.example.nfcapplication.repository.NdefTag
import com.example.nfcapplication.viewmodel.*
import com.example.nfcapplication.views.ndefTag.TestManufacturerName

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun Nfc() {
        val context = LocalContext.current
        val nfcViewModel: NfcViewModel = hiltViewModel()
        val nfcState by nfcViewModel.state.collectAsState()
    val itemInDatabase by nfcViewModel.manufacturerName.collectAsState()

        when (nfcState.state) {
            NfcNotSupported -> NfcNotSupportedView()
            NfcNotEnabled -> EnableNfcView(
                onSettingClicked = { context.startActivity(Intent(Settings.ACTION_NFC_SETTINGS)) },
                onCancelClicked = { nfcViewModel.enableNfc() }
            )
            EnableNfc -> NfcNotEnableView()
            ScanNfcTag -> ScanNfcTagView()
            NfcTagDiscovered ->
                when (val tag = nfcState.nfcScanningState.tag) {
                    is MifareClassicTag -> TestManufacturerName(itemInDatabase)
//                        MifareClassicTagView { nfcViewModel.showScanTag() }
                    is NdefTag -> NdefTagView(
                        generalTagInfo = tag.general,
                        nfcNdefMessage = tag.nfcNdefMessage,
                        onBackButtonClicked = { nfcViewModel.showScanTag() })
                    null -> LoadingView()
                }
        }
}