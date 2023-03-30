package com.example.nfcapplication.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.nfcapplication.R
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.android.common.theme.view.NordicAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnableNfcView(
    onSettingClicked: () -> Unit,
    onCancelClicked: () -> Unit
) {
    Column {
        NordicAppBar(text = stringResource(id = R.string.app_name))
        AlertDialog(
            onDismissRequest = { },
            title = { Text(text = stringResource(id = R.string.nfc_not_enabled)) },
            text = {
                Text(
                    text = stringResource(id = R.string.enable_nfc),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            confirmButton = {
                TextButton(
                    onClick = onSettingClicked
                ) {
                    Text(text = stringResource(id = R.string.setting))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onCancelClicked
                ) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            }
        )
    }
}

@Preview
@Composable
fun EnableNfcViewPreview() {
    NordicTheme {
        EnableNfcView(
            onSettingClicked = {},
            onCancelClicked = {}
        )
    }
}