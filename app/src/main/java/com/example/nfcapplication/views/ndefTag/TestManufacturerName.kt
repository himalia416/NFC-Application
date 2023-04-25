package com.example.nfcapplication.views.ndefTag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nfcapplication.viewmodel.ManufacturerName
import no.nordicsemi.android.common.theme.view.NordicAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestManufacturerName(itemsIn: List<ManufacturerName>) {
    Column {
        NordicAppBar(text = "Data from Database")
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            itemsIn.forEach { name ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = name.company)
                    Text(text = "$${name.country}")
                }
            }
        }
    }
}