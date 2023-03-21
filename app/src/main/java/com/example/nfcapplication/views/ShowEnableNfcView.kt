package com.example.nfcapplication.views

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import no.nordicsemi.android.common.theme.NordicTheme

@Composable
fun ShowEnableNfcView(
    onSettingClicked: () -> Unit,
    onCancelClicked: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = "Enable NFC") },
        text = { Text(text = "Please enable NFC from the device setting") },
        confirmButton = {
            TextButton(
                onClick = onSettingClicked
            ) {
                Text(text = "Setting")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCancelClicked
            ) {
                Text(text = "Cancel")
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