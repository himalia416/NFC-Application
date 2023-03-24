package com.example.nfcapplication.views

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nfcapplication.R
import com.example.nfcapplication.viewmodel.*
import no.nordicsemi.android.common.theme.view.NordicAppBar


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun NfcScanningScreen(onBackNavigation: () -> Unit) {
    Column {
        NordicAppBar(
            text = stringResource(id = R.string.app_name),
            onNavigationButtonClick = onBackNavigation
        )
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
                allAvailableTechnology = allAvailableTechnology
            )
            EnableNfc -> ShowNfcNotEnableView()
        }
    }
}