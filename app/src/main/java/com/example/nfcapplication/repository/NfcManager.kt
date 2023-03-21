package com.example.nfcapplication.repository

import android.app.Activity
import android.content.Context
import android.nfc.NfcAdapter
import android.nfc.NfcAdapter.ReaderCallback
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.nfc.tech.NfcA
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.nfcapplication.utility.bytesToHex
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject


class NfcManager @Inject constructor (
    @ApplicationContext private val context: Context,
) : DefaultLifecycleObserver {
     var nfcAdapter: NfcAdapter? = null
    var serialNumber: String = ""
    var isNfcSupported: Boolean = false

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        nfcAdapter = NfcAdapter.getDefaultAdapter(context)
        if (nfcAdapter == null) {
            Toast.makeText(context, "NFC not supported", Toast.LENGTH_LONG).show()
            return
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        val activity = owner as Activity
        nfcAdapter = NfcAdapter.getDefaultAdapter(context)
        if (nfcAdapter == null) {
            Toast.makeText(context, "NFC not supported", Toast.LENGTH_LONG).show()
            return
        } else {
            isNfcSupported = true
            val options = Bundle()
            options.putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 250)
            val readerFlags = NfcAdapter.FLAG_READER_NFC_A or
                    NfcAdapter.FLAG_READER_NFC_B or
                    NfcAdapter.FLAG_READER_NFC_F or
                    NfcAdapter.FLAG_READER_NFC_V or
                    NfcAdapter.FLAG_READER_NFC_BARCODE or
                    NfcAdapter.FLAG_READER_NO_PLATFORM_SOUNDS


            // Enable ReaderMode for all types of card and disable platform sounds
            nfcAdapter?.enableReaderMode(
                context as Activity,
                { tag -> onTagDiscovered(tag) },
                readerFlags,
                options
            )
        }
    }

    private fun onTagDiscovered(tag: Tag?) {
        try {
            if (tag == null) return
            val iso = IsoDep.get(tag) ?: return
            val nfc = NfcA.get(tag)
            val atqa: ByteArray = nfc.atqa
            val sak: Short = nfc.sak
            Log.d("readNfcTag", "atqa size and sak: ${atqa.size}  $sak")
            serialNumber = bytesToHex(tag.id)
            Log.d("readNfcTag", "Serial Number: $serialNumber")
            iso.timeout = 1000
            iso.connect()
            iso.close()
            while (true) {
                try {
                    iso.connect()
                    Thread.sleep(100)
                    iso.close()
                } catch (e: IOException) {
                    onCardRemoved()
                    break
                } catch (e: InterruptedException) {
                    onCardRemoved()
                    break
                }
            }
        } catch (e: IOException) {
            Toast.makeText(context, "Tag disconnected. Reason: " + e.message, Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        if (nfcAdapter != null)
            nfcAdapter?.disableReaderMode(context as Activity);
    }

    private fun onCardRemoved() {
        return
    }

}