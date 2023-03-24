package com.example.nfcapplication.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nfcapplication.R
import com.example.nfcapplication.utility.serialNumberFormatter
import com.example.nfcapplication.utility.splitter
import no.nordicsemi.android.common.theme.NordicTheme

@Composable
fun ShowTagInformationView(
    serialNumber: String,
    allAvailableTechnology: List<String>,
) {
    if (serialNumber.isNotEmpty()) {
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row {
                    Text(
                        text = stringResource(id = R.string.serial_number),
                        modifier = Modifier.padding(end = 16.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = serialNumberFormatter(serialNumber))
                }
                Text(
                    text = stringResource(id = R.string.available_tag_technologies),
                    fontWeight = FontWeight.Bold
                )
                LazyColumn(contentPadding = PaddingValues(8.dp)) {
                    items(items = allAvailableTechnology) { eachTag ->
                        Text(text = splitter(eachTag))
                    }
                }
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview
@Composable
fun ShowTagInformationViewPreview() {
    NordicTheme {
        ShowTagInformationView(
            serialNumber = "2345ca8709",
            allAvailableTechnology = listOf("ISO", "NFCA", "NFCF"),
        )
    }
}