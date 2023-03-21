package com.example.nfcapplication

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.example.nfcapplication.utility.bytesToHex
import dagger.hilt.android.AndroidEntryPoint
import no.nordicsemi.android.common.theme.NordicTheme


@AndroidEntryPoint
class NFCActivity : ComponentActivity() {
    var nfcAdapter: NfcAdapter? = null
    var pendingIntent: PendingIntent? = null
    lateinit var mIntentFilters: Array<IntentFilter>
    private lateinit var mTechLists: Array<Array<String>>
    private val TAG = "AAA"


    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NordicTheme {
                initAdapter()
                initFields()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun initFields() {
        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_MUTABLE
        )
        mIntentFilters = arrayOf(IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED))
        mTechLists = arrayOf(
            arrayOf(Ndef::class.java.name), arrayOf(
                NdefFormatable::class.java.name
            )
        )
        mIntentFilters.forEach {
            Log.d(TAG, "initFields: ${it.getAction(0)}")
        }
        Log.d(TAG, "intent action on ${object {}.javaClass.enclosingMethod?.name} ${intent.action}")
        Log.d(
            TAG,
            "initFields: intentfilters ${mIntentFilters.forEach { it.getAction(0) }} and \n ${mTechLists.size}"
        )
    }

    private fun initAdapter() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            Log.d("AAA", "Adapter initialized")
            finish()
            return
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onResume() {
        super.onResume()
        initAdapter()
        initFields()
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, mIntentFilters, mTechLists)
        Log.d("AAA", "FGD enabled")
        if (NfcAdapter.ACTION_TECH_DISCOVERED == intent.action) {
            Log.d(TAG, "onResume: yayy")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
        Log.d("AAA", "FGD disabled")

    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        initFields()
        Log.d(TAG, "intent action: ${intent.action}")
        val tag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(NfcAdapter.EXTRA_TAG, Tag::class.java)
        } else {
            intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
        }
        val nfc = NfcA.get(tag)
        Log.d(TAG, "NFCA maximum Transceive Length: ${nfc.maxTransceiveLength}")
        val atqa: ByteArray = nfc.atqa
        val sak: Short = nfc.sak
        Log.d(TAG, "atqa and sak:  ${atqa.size} and $sak")

//        nfc.connect()
//        if (nfc.isConnected){
//            val receivedData:ByteArray= nfc.transceive(NFC_READ_COMMAND)
//        } else Log.e("ans", "Not connected")

        Log.d("AAA", "onNewIntent: tag= $tag")
        val serialId = bytesToHex(tag?.id)
        Log.d("AAA", "Serial number $serialId")
        tag?.let { eachTag ->
            val a = eachTag.toString()
            a.split("[", "]")[1].split(", ").forEach {
                Log.d("AAA", "Each tag: $it ")
            }
        }
        val a = MifareClassic.get(intent.getParcelableExtra(NfcAdapter.EXTRA_TAG))
        Log.d(TAG, "MifareUltralight: $a")
//        Log.d("AAA", " ATQA/SENS_RES ${tag.techList.}")
        tag?.let {
            val ndef = Ndef.get(tag)
            if (ndef != null) {
                ndef.connect()
                val message = ndef.ndefMessage
                ndef.cachedNdefMessage
                message?.let { ndefMessage ->
                    val payload = String(ndefMessage.records[0].payload)
                    // handle the payload as required
                    Log.d("AAA", "payload: $payload")
                }
                Log.d("AAA", "message: $message")
                ndef.close()
            } else {
                /*
                ndef message is
                 */
                val ndefMessage: NdefMessage? = null
                val ndefFormatable: NdefFormatable = NdefFormatable.get(tag)
                Log.d("AAA", "ndefFormatable: $ndefFormatable ")
                try {
                    ndefFormatable.connect()
                    Log.d("AAA", "isNdefFromatble connected: ${ndefFormatable.isConnected} ")
                    // Format a tag as NDEF, and write a NdefMessage.
                    // Create a new NdefMessage to write
                    val message = NdefMessage(
                        arrayOf(
                            NdefRecord.createTextRecord("en", "Hello, World!")
                        )
                    )
//                    ndefFormatable.format(NdefMessage(byteArrayOf()))
//                    val ndefTag2 = Ndef.get(tag)
//                    ndefTag2.connect()
//                    ndefTag2.writeNdefMessage(message)
                } finally {
                    try {
                        ndefFormatable.close()
                    } catch (_: Exception) {
                    }
                }
            }
//            } catch (e: java.lang.Exception) {
//                Log.d("AAA", "Can't read the tag!")
//            }
            val tagList = tag.techList
            Log.d("AAA", "onResume: tagLIst = ${tagList.size} ")
            Log.d("AAA", "onResume: tagLIst = ${tagList.toList()} ")
        }
        /*       if (getIntent() != null) {
                   if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent?.action || NfcAdapter.ACTION_TECH_DISCOVERED == intent?.action) {
       //                val tag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
       //                    intent.getParcelableExtra(NfcAdapter.EXTRA_TAG, Tag::class.java)
       //                } else {
       //                    intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
       //                }

                   }
               }*/
    }

/*    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("AAA", "onNewIntent: $intent ")
        Log.d("AAA", "onNewIntent: ${intent?.action} ")
        val tag: Tag? = intent?.getParcelableExtra(NfcAdapter.EXTRA_TAG)
        setIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        Log.d("AAA", "onNewIntent: intent action = ${intent?.action} ")
        if (intent?.action == NfcAdapter.ACTION_TAG_DISCOVERED || intent?.action == NfcAdapter.ACTION_NDEF_DISCOVERED) {
            Log.d("AAA", "onNewIntent: intent?.action ")
            // Handle the NFC tag here
            val tag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(NfcAdapter.EXTRA_TAG, Tag::class.java)
            } else {
                intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
            }
            tag?.let {
                val ndef = Ndef.get(tag)
                try {
                    ndef?.connect()
                    val message = ndef?.ndefMessage
                    message?.let { ndefMessage ->
                        val payload = String(ndefMessage.records[0].payload)
                        // handle the payload as required
                        Log.d("AAA", "payload: $payload")
                    }
                    Log.d("AAA", "message: $message")
                    ndef?.close()
                } catch (e: java.lang.Exception) {
                    Log.d("AAA", "Can't read the tag!")
                }
                val tagList = tag.techList
                Log.d("AAA", "onResume: tagLIst = $tagList ")
            }
        }
    }*/
}

@Composable
fun NfcReader3() {
    val context = LocalContext.current
    val adapter = NfcAdapter.getDefaultAdapter(context)

    if (adapter == null) {
        Toast.makeText(context, "NFC is not available", Toast.LENGTH_LONG).show()
        return
    } else Toast.makeText(context, "NFC is available", Toast.LENGTH_LONG).show()

    val intentFilter = arrayOf(IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED))
    Log.d("AAA", "NfcReader1: $intentFilter")

    DisposableEffect(adapter) {
        Log.d("AAA", "NfcReader1: this is in the disposable effect ! ")
        val foregroundIntent = Intent(context, context.javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        val foregroundPendingIntent = PendingIntent.getActivity(
            context, 0, foregroundIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        adapter.enableForegroundDispatch(
            context as Activity,
            foregroundPendingIntent,
            null,
            null
//            intentFilter,
//            arrayOf(arrayOf<String>(NfcF::class.java.name))
        )
        onDispose {
            Log.d("AAA", "NfcReader1: onDispose")
            adapter.disableForegroundDispatch(context)
        }
    }

    if (adapter.isEnabled) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Hold an NFC tag near the device to read it.",
                textAlign = TextAlign.Center
            )
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "NFC is not enabled on this device.",
                textAlign = TextAlign.Center
            )
        }
    }
}

