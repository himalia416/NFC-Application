package com.example.nfcapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.nfc.tech.NfcF
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("UnspecifiedImmutableFlag")
@Composable
fun NfcReader() {
    val context = LocalContext.current
    val adapter = NfcAdapter.getDefaultAdapter(context)

    if (adapter == null) {
        Toast.makeText(context, "NFC is not available", Toast.LENGTH_LONG).show()
        return
    } else Toast.makeText(context, "NFC is available", Toast.LENGTH_LONG).show()


    val nfcIntentFilter = remember {
        IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)
    }
    val intentFilter = arrayOf(IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED))

    val intent = remember { mutableStateOf(arrayOf(IntentFilter())) }
    val techList = remember { mutableStateListOf<Array<Array<String>>>() }

    DisposableEffect(adapter) {
        val foregroundIntent = Intent(context, context.javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        val foregroundPendingIntent = getActivity(
            context,
            0,
            foregroundIntent,
            FLAG_MUTABLE
        )
        intent.value =  intentFilter
        techList.add(
            arrayOf(
                arrayOf(Ndef::class.java.name), arrayOf(
                    NdefFormatable::class.java.name
                )
            )
        )
        adapter.enableForegroundDispatch(
            context as Activity,
            foregroundPendingIntent,
            intentFilter,
            arrayOf(arrayOf<String>(NfcF::class.java.name), arrayOf(
                NdefFormatable::class.java.name
            ))
        )
        onDispose {
            adapter.disableForegroundDispatch(context)
        }
    }

    val handleNfcIntent = { intent1: Intent ->
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent1.action ||
            NfcAdapter.ACTION_TAG_DISCOVERED == intent1.action ||
            NfcAdapter.ACTION_TECH_DISCOVERED == intent1.action
        ) {
            Log.d("AAA", "NfcReader: this reached in the action discovered! ")
            val tag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent1.getParcelableExtra(NfcAdapter.EXTRA_TAG, Tag::class.java)
            } else {
                intent1.getParcelableExtra(NfcAdapter.EXTRA_TAG)
            }
            Log.d("AAA", "NfcReader: this reached in the tag $tag ")
            tag?.let {
                val ndef = Ndef.get(it)
                ndef?.connect()
                val message = ndef?.ndefMessage
                message?.let {
                    val payload = String(it.records[0].payload)
                    // handle the payload as required
                    Log.d("AAA", "payload: $payload")
                }
                Log.d("AAA", "message: $message")
                ndef?.close()
            }
                ?: run {
                    Toast.makeText(context, "Cannot Read From Tag.", Toast.LENGTH_LONG).show()
                }
        }
    }


    DisposableEffect(context) {
        Log.d("AAA", "NfcReader: this reached in the code above the receiver! ")
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d("AAA", "onReceive: this reached on receive ")
                handleNfcIntent(intent ?: Intent())
            }
        }
        context.registerReceiver(receiver, nfcIntentFilter)
        onDispose {
            Log.d("AAA", "NfcReader: Unregister receiver ")
            context.unregisterReceiver(receiver)
        }
    }
}
