package com.example.nfcapplication.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.nfcapplication.R
import no.nordicsemi.android.common.theme.view.NordicAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MifareClassicTagView(
    onBackNavigation: () -> Unit
){
    Column {
        NordicAppBar(text = stringResource(id = R.string.mifare_classic_tag),
        onNavigationButtonClick = onBackNavigation
        )
        Text(text = "This is miFareClassic tag!")
    }
}