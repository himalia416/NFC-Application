package com.example.nfcapplication

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nfcapplication.viewmodel.NfcViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScannerView(serialNumber: String, tagList: List<String>) {
    Log.d("AAA", "ScannerView: $serialNumber ${tagList.size} ")
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(32.dp)
    ) {
        Column {
            Text(
                text = "Tag Serial Number",
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = serialNumber,
                modifier = Modifier.padding(16.dp)
            )
        }
        LazyColumn(contentPadding = PaddingValues(8.dp)) {
            stickyHeader {
                Surface(shadowElevation = 4.dp) {
                    Text(
                        text = "Tags Technology available",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            items(tagList) { tagList ->
                Text(
                    text = tagList,
                    modifier = Modifier.padding(start = 16.dp)
                )

            }
        }
    }
}

@Composable
fun ScanningView() {

}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun NFCReader3(nfcViewModel: NfcViewModel) {
    val context = LocalContext.current
    val intent = remember { Intent(context, MainActivity::class.java) }
    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
    val pendingIntent = remember {
        PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_MUTABLE
        )
    }
    val intentFilters = remember { arrayOf(IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)) }
    val techLists = remember { arrayOf(arrayOf(Ndef::class.java.name), arrayOf(NdefFormatable::class.java.name)) }

    DisposableEffect(Unit) {
        val nfcAdapter = NfcAdapter.getDefaultAdapter(context)
        if (nfcAdapter == null) {
            Toast.makeText(context, "NFC not supported", Toast.LENGTH_LONG).show()
            return@DisposableEffect onDispose {}
        }
        nfcAdapter.enableForegroundDispatch(context as Activity, pendingIntent, intentFilters, techLists)
        onDispose {
            nfcAdapter.disableForegroundDispatch(context)
        }
    }
//
//    val allTags by nfcViewModel.allTags.
//    Text("All Tags: $allTags")
}