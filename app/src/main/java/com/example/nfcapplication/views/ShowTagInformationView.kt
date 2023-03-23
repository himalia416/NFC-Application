package com.example.nfcapplication.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.nordicsemi.android.common.theme.NordicTheme

@Composable
fun ShowTagInformationView(
    serialNumber: String,
    allAvailableTechnology: List<String>,
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                Text(
                    text = "Serial Number",
                    modifier = Modifier.padding(end = 16.dp),
                    fontWeight = FontWeight.Bold
                )
                Text(text = serialNumber)
            }
            Text(
                text = "Available tags",
                fontWeight = FontWeight.Bold
            )
            LazyColumn(contentPadding = PaddingValues(8.dp)) {
                items(items = allAvailableTechnology) { eachTag ->
                    Text(text = eachTag)
                }
            }
        }
    }
}

@Preview
@Composable
fun ShowTagInformationViewPreview() {
    NordicTheme {
        ShowTagInformationView(
            serialNumber = "23:45:87:09",
            allAvailableTechnology = listOf("ISO", "NFCA", "NFCF"),
        )
    }
}