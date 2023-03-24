package com.example.nfcapplication.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Nfc
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nfcapplication.R
import no.nordicsemi.android.common.theme.NordicTheme

@Composable
fun ShowNfcNotSupportedView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = Icons.Filled.Nfc, contentDescription = null)
            Text(text = stringResource(id = R.string.nfc_not_supported))
        }
    }
}

@Preview
@Composable
fun ShowNfcNotSupportedViewPreview(){
    NordicTheme {
        ShowNfcNotSupportedView()
    }
}