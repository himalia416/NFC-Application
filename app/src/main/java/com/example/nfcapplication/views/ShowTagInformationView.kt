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
fun ShowTagInformationView(sn: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = sn, modifier = Modifier.align(Alignment.Center))
    }
}

@Preview
@Composable
fun ShowTagInformationViewPreview(){
    NordicTheme {
        ShowTagInformationView(sn = "23:45:87:09")
    }
}