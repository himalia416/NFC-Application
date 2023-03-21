package com.example.nfcapplication

import android.content.Intent
import android.nfc.tech.*
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nfcapplication.repository.NfcScanningManager
import com.example.nfcapplication.viewmodel.NfcNotEnabled
import com.example.nfcapplication.viewmodel.NfcViewModel
import com.example.nfcapplication.viewmodel.ScanNfcTag
import com.example.nfcapplication.viewmodel.ShowWelcomeDialog
import com.example.nfcapplication.views.*
import dagger.hilt.android.AndroidEntryPoint
import no.nordicsemi.android.common.theme.NordicTheme
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var nfcManager: NfcScanningManager

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(nfcManager)

        setContent {
            NordicTheme {
                val nfcViewModel: NfcViewModel = hiltViewModel()
                val nfcState by nfcViewModel.state.collectAsState()
                val isNfcSupported by nfcState.isNfcSupported.collectAsState()
                val sn by nfcViewModel.serialNumber.collectAsState()
                val isNfcEnabled by nfcState.isNfcEnabled.collectAsState()

                when (nfcState.state) {
                    is ShowWelcomeDialog -> ShowWelcomeDialogView(
                        onButtonClicked = { nfcViewModel.showHomePage() }
                    )
                    ScanNfcTag -> if (!isNfcSupported) {
                        ShowNfcNotSupportedView()
                    } else if (!isNfcEnabled) {
                        ShowEnableNfcView(
                            onSettingClicked = { enableNfcSetting() },
                            onCancelClicked = { nfcViewModel.showNfcNotEnabledPage() }
                        )
                    } else {
                        if (sn.isNotEmpty()) {
                            ShowTagInformationView(sn)
                        } else ScanNfcTagView()
                    }
                    NfcNotEnabled -> ShowNfcNotEnableView()
                }
            }
        }
    }

    private fun enableNfcSetting() {
        startActivity(Intent(Settings.ACTION_NFC_SETTINGS))
    }
}
