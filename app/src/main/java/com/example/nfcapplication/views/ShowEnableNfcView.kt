package com.example.nfcapplication.views

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.nfcapplication.R
import no.nordicsemi.android.common.theme.NordicTheme

@Composable
fun ShowEnableNfcView(
    onSettingClicked: () -> Unit,
    onCancelClicked: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = stringResource(id = R.string.nfc_not_enabled)) },
        text = { Text(text = stringResource(id = R.string.enable_nfc)) },
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

@Preview
@Composable
fun ShowEnableNfcViewPreview(){
    NordicTheme {
        ShowEnableNfcView(
            onSettingClicked = {},
            onCancelClicked = {}
        )
    }
}