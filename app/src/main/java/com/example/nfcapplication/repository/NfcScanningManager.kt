package com.example.nfcapplication.repository

import android.app.Activity
import android.content.Context
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.nfcapplication.data.ReaderFlag
import com.example.nfcapplication.utility.bytesToHex
import com.example.nfcapplication.utility.tagTypeSplitter
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

data class NfcScanningState(
    val serialNumber: String = "",
    val tagTechnology: List<String> = emptyList(),
    val isNfcSupported: Boolean = false,
    val isNfcEnabled: Boolean = false,
)

@Singleton
class NfcScanningManager @Inject constructor(
    @ApplicationContext private val context: Context,
) : DefaultLifecycleObserver {
    private val TAG = NfcScanningManager::class.java.simpleName
    private var nfcAdapter: NfcAdapter? = null
    private val _nfcScanningState: MutableStateFlow<NfcScanningState> =
        MutableStateFlow(NfcScanningState())
    val nfcScanningState = _nfcScanningState.asStateFlow()

    override fun onResume(owner: LifecycleOwner) {
        nfcAdapter = NfcAdapter.getDefaultAdapter(context)

        nfcAdapter?.let {
            _nfcScanningState.value = _nfcScanningState.value.copy(isNfcSupported = true)
            if (it.isEnabled) {
                _nfcScanningState.value = _nfcScanningState.value.copy(isNfcEnabled = true)
                // Returns an array of all the enum member and combines each values using  Bitwise OR operator.
                val readerFlags =
                    enumValues<ReaderFlag>().fold(0) { acc, flag -> acc or flag.value }
                // Enable ReaderMode for all types of card and disable platform sounds
                it.enableReaderMode(owner as Activity, ::onTagDiscovered, readerFlags, null)
            } else Log.d(TAG, "NFC not enabled on the device!")
        } ?: run {
            Log.d(TAG, "NFC not supported on the device!")
            return
        }

    }

    private fun onTagDiscovered(tag: Tag?) {
        try {
            if (tag == null) return
            _nfcScanningState.value = _nfcScanningState.value.copy(
                serialNumber = bytesToHex(tag.id),
                tagTechnology = emptyList()
            )
            Log.d(TAG, "Serial Number: ${bytesToHex(tag.id)}")
            tagTypeSplitter(tag = tag.toString()).forEach { t ->
                val tagTech = _nfcScanningState.value.tagTechnology
                // Check for duplicate
                if (!tagTech.any { it == t }) {
                    _nfcScanningState.value =
                        _nfcScanningState.value.copy(tagTechnology = tagTech + listOf(t))
                    Log.d("readNfcTag", "Each tag: $t")
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Tag disconnected. Reason: " + e.message)
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        // Disable ReaderMode
        if (nfcAdapter != null) nfcAdapter?.disableReaderMode(owner as Activity)
    }
}