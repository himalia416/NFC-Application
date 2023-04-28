package com.example.profile_nfc.scanning.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.profile_nfc.R
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.android.common.theme.view.NordicAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanNfcTagView() {
    Column {
        NordicAppBar(text = stringResource(id = R.string.app_name))
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.nfc_scanning),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(200.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
                Text(text = stringResource(id = R.string.scan_nfc_tag))
            }
        }
    }
}

@Preview
@Composable
fun ScanNfcTagViewPreview() {
    NordicTheme {
        ScanNfcTagView()
    }
}