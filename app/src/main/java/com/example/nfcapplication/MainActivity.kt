package com.example.nfcapplication

import android.app.Activity
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nfcapplication.utility.bytesToHexString
import com.example.nfcapplication.viewmodel.NfcViewModel
import com.example.nfcapplication.viewmodel.ShowTagDiscoveredPage
import com.example.nfcapplication.viewmodel.ShowWelcomeDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import no.nordicsemi.android.common.theme.NordicTheme
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var nfcAdapter: NfcAdapter? = null
    private var activity = this@MainActivity

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NordicTheme {
                nfcAdapter = NfcAdapter.getDefaultAdapter(this)
                val nfcViewModel: NfcViewModel = hiltViewModel()
                val nfcState by nfcViewModel.state.collectAsState()

                when (nfcState.state) {
                    is ShowWelcomeDialog -> ShowWelcomeDialogView(
                        onButtonClicked = { nfcViewModel.showHomePage() }
                    )
//                    ShowScanningPage -> ShowScanningView()
                    ShowTagDiscoveredPage -> ShowTagDiscoveredView(nfcAdapter, activity)
                }
            }
        }
    }
}

@Composable
fun ScanNfcTagView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Scan an NFC tag", modifier = Modifier.align(Alignment.Center))
    }
}

@Preview
@Composable
fun ScanNfcTagViewPreview() {
    NordicTheme {
        ScanNfcTagView()
    }
}

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

@Composable
fun ShowTagDiscoveredView(nfcAdapter: NfcAdapter?, activity: MainActivity){
    val context = LocalContext.current
    val scope: CoroutineScope = rememberCoroutineScope()
    if (nfcAdapter == null) {
        Toast.makeText(context, "NFC not supported", Toast.LENGTH_LONG).show()
        return
    }

    var id by rememberSaveable { mutableStateOf("") }

    class MyReaderCallback : NfcAdapter.ReaderCallback {
        override fun onTagDiscovered(tag: Tag) {
            Log.d("Tag Discovered", "Tag Discovered.")

            val idm = tag.id
            val idmString = bytesToHexString(idm)
            Log.d("Serial Number", idmString)
            id = idmString

        }
    }

    val readerFlags = NfcAdapter.FLAG_READER_NFC_A or
            NfcAdapter.FLAG_READER_NFC_B or
            NfcAdapter.FLAG_READER_NFC_F or
            NfcAdapter.FLAG_READER_NFC_V or
            NfcAdapter.FLAG_READER_NFC_BARCODE or
            NfcAdapter.FLAG_READER_NO_PLATFORM_SOUNDS


    // Enable ReaderMode for all types of card and disable platform sounds
    DisposableEffect(nfcAdapter) {

        nfcAdapter.enableReaderMode(
            context as Activity,
            MyReaderCallback(),
            readerFlags,
            null
        )
        onDispose { nfcAdapter.disableReaderMode(activity) }
    }
}

