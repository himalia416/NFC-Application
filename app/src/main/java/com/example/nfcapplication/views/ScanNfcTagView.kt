package com.example.nfcapplication.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.nfcapplication.R
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.android.common.theme.view.NordicAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanNfcTagView() {
    Column {
        NordicAppBar(text = stringResource(id = R.string.app_name))
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.scan_nfc_tag),
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}

@Preview
@Composable
fun ScanNfcTagViewPreview() {
    NordicTheme {
        ScanNfcTagView()
    }
}