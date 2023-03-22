package com.example.nfcapplication.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Nfc
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.nfcapplication.R
import no.nordicsemi.android.common.theme.NordicTheme

@Composable
fun ShowNfcNotEnableView() {
    OutlinedCard(
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(imageVector = Icons.Default.Nfc, contentDescription = null)
        Text(
            text = stringResource(id = R.string.instruction_to_enable_nfc)
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