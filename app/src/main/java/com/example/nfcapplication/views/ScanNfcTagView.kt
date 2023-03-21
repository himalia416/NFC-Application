package com.example.nfcapplication.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import no.nordicsemi.android.common.theme.NordicTheme

@Composable
fun ScanNfcTagView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Scan an NFC tag", modifier = Modifier.align(Alignment.Center))
    }
}

@Preview
@Composable
fun ScanNfcTagViewPreview(){
    NordicTheme {
        ScanNfcTagView()
    }
}