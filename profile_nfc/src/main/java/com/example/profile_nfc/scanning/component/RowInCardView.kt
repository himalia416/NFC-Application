package com.example.profile_nfc.scanning.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.nordicsemi.android.common.theme.NordicTheme
import androidx.compose.ui.text.TextStyle

@Composable
fun RowInCardView(
    firstItem: String,
    secondItem: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium
) {
    Row {
        Text(
            text = firstItem,
            modifier = modifier.padding(end = 16.dp),
            style = textStyle
        )
        Text(text = secondItem)
    }
}

@Preview
@Composable
fun RowInCardViewPreview() {
    NordicTheme {
        RowInCardView(
            firstItem = "Maximum message size",
            secondItem = "53 Bytes",
        modifier = Modifier.padding(end = 16.dp),
        )
    }
}