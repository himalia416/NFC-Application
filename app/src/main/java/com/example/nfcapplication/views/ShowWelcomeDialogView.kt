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
fun ShowWelcomeDialogView(onButtonClicked: () -> Unit) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = stringResource(id = R.string.welcome)) },
        text = { Text(text = stringResource(id = R.string.welcome_message)) },
        confirmButton = {
            TextButton(
                onClick = onButtonClicked
            ) {
                Text(text = stringResource(id = R.string.ok))
            }
        },
    )
}

@Preview
@Composable
fun ShowWelcomeDialogViewPreview(){
    NordicTheme {
        ShowWelcomeDialogView(
            onButtonClicked = {}
        )
    }
}