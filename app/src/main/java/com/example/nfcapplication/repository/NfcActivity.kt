package com.example.nfcapplication.repository

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.nfc.tech.NfcA
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import com.example.nfcapplication.utility.bytesToHex
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Test @Inject constructor(
    @ApplicationContext private val context: Context
) : ComponentActivity() {
    private var nfcAdapter: NfcAdapter? = null
    private var pendingIntent: PendingIntent? = null
    lateinit var mIntentFilters: Array<IntentFilter>
    private lateinit var mTechLists: Array<Array<String>>
    private var isNfcSupported = false

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
        initFields()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun initFields() {
        val intent = Intent(context as Activity, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        pendingIntent = PendingIntent.getActivity(
            context,
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
    }

    private fun initAdapter() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(context)
        if (nfcAdapter == null) {
            Toast.makeText(context, "NFC not supported", Toast.LENGTH_LONG).show()
            return
        } else isNfcSupported = true
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onResume() {
        super.onResume()
        initAdapter()
        initFields()
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, mIntentFilters, mTechLists)
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        readNfcTag(intent = intent)
    }

    private fun readNfcTag(intent: Intent) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action
            || NfcAdapter.ACTION_TECH_DISCOVERED == intent.action
            || NfcAdapter.ACTION_TAG_DISCOVERED == intent.action
        ) {
            val allTags: MutableList<String> = mutableListOf()
            val tag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(NfcAdapter.EXTRA_TAG, Tag::class.java)
            } else intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)

            val nfc = NfcA.get(tag)
            val atqa: ByteArray = nfc.atqa
            val sak: Short = nfc.sak
            Log.d("readNfcTag", "atqa size and sak: ${atqa.size}  $sak")
            val serialNumber = bytesToHex(tag?.id)
            Log.d("readNfcTag", "Serial Number: $serialNumber")
            tag?.let { eachTag ->
                val a = eachTag.toString()
                a.split("[", "]")[1].split(", ").forEach {
                    allTags.add(it)
                    Log.d("readNfcTag", "Each tag: $it")
                }
                val ndef = Ndef.get(tag)
                if (ndef != null) {
                    ndef.connect()
                    val message = ndef.ndefMessage
                    ndef.cachedNdefMessage
                    message?.let { ndefMessage ->
                        val payload = String(ndefMessage.records[0].payload)
                        // handle the payload as required
                        Log.d("TAG", "Ndef payload message: $payload")
                    }
                    ndef.close()
                } else {
                    val ndefFormatable: NdefFormatable = NdefFormatable.get(tag)
                    ndefFormatable.connect()

                }
            }
        }
    }

}
