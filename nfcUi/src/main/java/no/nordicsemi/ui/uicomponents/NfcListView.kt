package no.nordicsemi.ui.uicomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.nfcui.R

@Composable
fun NfcListView(
    title: String,
    list: List<String>
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
        list.onEach {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall
            )
        }

    }
}

@Preview
@Composable
fun NfcListViewPreview() {
    NordicTheme {
        NfcListView(
            title = stringResource(id = R.string.available_tag_technologies),
            list = listOf("NfcA, Ndef,")
        )
    }
}