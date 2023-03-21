package com.example.nfcapplication.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Nfc
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import no.nordicsemi.android.common.theme.NordicTheme

@Composable
fun ShowNfcNotEnableView() {
    OutlinedCard(
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(imageVector = Icons.Default.Nfc, contentDescription = null)
        Text(
            text = "NFC is not Enabled in this device.\n \nGo to the Settings > Connected Devices to turn NFC on. "
        )
    }
}

@Preview
@Composable
fun ShowNfcNotEnableViewPreview(){
    NordicTheme {
        ShowNfcNotEnableView()
    }
}