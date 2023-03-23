package com.example.nfcapplication.repository

import android.app.Activity
import android.content.Context
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.NfcA
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.nfcapplication.utility.bytesToHex
import com.example.nfcapplication.utility.splitter
import com.example.nfcapplication.utility.tagTypeSplitter
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NfcScanningManager @Inject constructor(
    @ApplicationContext private val context: Context,
) : DefaultLifecycleObserver {
    private val TAG = NfcScanningManager::class.java.simpleName
    private var nfcAdapter: NfcAdapter? = null
    private val _serialNumber: MutableStateFlow<String> = MutableStateFlow("")
    val serialNumber = _serialNumber.asStateFlow()
    private val _tagTechnology: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val tagTechnology = _tagTechnology.asStateFlow()
    private val _isNfcSupported: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isNfcSupported = _isNfcSupported.asStateFlow()
    private val _isNfcEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isNfcEnabled = _isNfcEnabled.asStateFlow()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        initAdapter()
    }

    private fun initAdapter() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(context)
        if (nfcAdapter == null) {
            return
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        val activity = owner as Activity
        initAdapter()
        _isNfcSupported.value = true
        if (!nfcAdapter!!.isEnabled) {
            Log.d(TAG, "NFC not supported in  the device")
        } else {
            _isNfcEnabled.value = true
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
                activity,
                { tag -> onTagDiscovered(tag) },
                readerFlags,
                options
            )
        }
    }

    private fun onTagDiscovered(tag: Tag?) {
        try {
            if (tag == null) return
            val nfc = NfcA.get(tag)
            val atqa: ByteArray = nfc.atqa
            val sak: Short = nfc.sak
            Log.d(TAG, "atqa size and sak: ${atqa.size}  $sak")
            _serialNumber.value = bytesToHex(tag.id)
            Log.d(TAG, "Serial Number: ${_serialNumber.value}")
            tag.let { eachTag ->
                val a = eachTag.toString()
                tagTypeSplitter(a).forEach { t ->
                    // Check for duplicate
                    if (!_tagTechnology.value.any { it == t }) {
                        _tagTechnology.value = _tagTechnology.value + listOf(splitter(t))
                        Log.d("readNfcTag", "Each tag: $t")
                    }
                }
            }
        } catch (e: IOException) {
            Toast.makeText(context, "Tag disconnected. Reason: " + e.message, Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        val activity = owner as Activity
        if (nfcAdapter != null)
            nfcAdapter?.disableReaderMode(activity)
    }
}