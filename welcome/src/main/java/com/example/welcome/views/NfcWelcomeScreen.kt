package com.example.welcome.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.welcome.R
import com.example.welcome.viewmodel.WelcomeViewModel
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.android.common.theme.view.NordicAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NfcWelcomeScreen() {
    val viewModel: WelcomeViewModel = hiltViewModel()
    val firstRun by viewModel.firstRun.collectAsStateWithLifecycle()

    Box {
        Column {
            if (firstRun) {
                NordicAppBar(text = stringResource(id = R.string.welcome))
            } else {
                NordicAppBar(
                    text = stringResource(R.string.about_app),
                    onNavigationButtonClick = { viewModel.navigateUp() },
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.nfc),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .padding(16.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )

                Spacer(modifier = Modifier.size(32.dp))

                Text(
                    text = stringResource(id = R.string.welcome_message),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.size(76.dp))
            }
        }
        if (firstRun) {
            ElevatedButton(
                onClick = { viewModel.navigateUp() },
                colors = ButtonDefaults.buttonColors(),
                modifier = Modifier
                    .padding(16.dp)
                    .width(150.dp)
                    .align(Alignment.BottomCenter),
            ) {
                Text(text = stringResource(id = R.string.start))
            }
        }
    }
}

@Preview
@Composable
fun ShowWelcomeDialogViewPreview() {
    NordicTheme {
        NfcWelcomeScreen()
    }
}