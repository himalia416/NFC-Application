package no.nordicsemi.ui.uicomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import no.nordicsemi.android.common.theme.NordicTheme

@Composable
fun NfcRowView(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    tooltipText: String? = null,
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleMedium
            )
            tooltipText?.let {
                TooltipPopUp(tooltipText)
            }

        }
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Preview
@Composable
fun NfcRowViewPreview() {
    NordicTheme {
        NfcRowView(
            title = "Maximum message size",
            description = "53 Bytes",
        )
    }
}