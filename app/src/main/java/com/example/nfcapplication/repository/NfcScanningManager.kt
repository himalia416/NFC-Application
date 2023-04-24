package com.example.nfcapplication.repository

import android.app.Activity
import android.content.Context
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.*
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.nfcapplication.data.*
import com.example.nfcapplication.utility.toHex
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

data class NfcScanningState(
    val isNfcSupported: Boolean = false,
    val isNfcEnabled: Boolean = false,
    val tag: NfcTag? = null,
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
            tag?.let {
                Log.d(TAG, "Serial Number: ${it.id.toHex()}")
                val (allTags, maxTransceiveLength, transceiveTimeOut) = getAllTagAndTransceiveLength(
                    tag
                )
                val generalTagInformation = GeneralTagInformation(
                    serialNumber = it.id.toHex(),
                    tagTechnology = allTags,
                    icManufacturerName = getIcManufacturerName(tag),
                    maxTransceiveLength = maxTransceiveLength,
                    transceiveTimeout = transceiveTimeOut.toString()
                )
                when  {
                    it.techList.contains(Ndef::class.java.name) -> { onNdefTagDiscovered(it, generalTagInformation) }
                    it.techList.contains(MifareClassic::class.java.name) -> { onMifareClassicTagDiscovered(it, generalTagInformation) }
                    else -> {}
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Tag disconnected. Reason: " + e.message)
        }
    }

    private fun getAllTagAndTransceiveLength(tag: Tag): Triple<List<String>, Int, Int> {
        val allTagList = tag.techList.map { it.split('.').last() }
        val (maxTransceiveLength, timeout) = when {
            tag.techList.contains(NfcA::class.java.name) -> NfcA.get(tag).use { Pair(it.maxTransceiveLength, it.timeout) }
            tag.techList.contains(NfcB::class.java.name) -> NfcB.get(tag).use { Pair(it.maxTransceiveLength, 0) }
            tag.techList.contains(NfcF::class.java.name) -> NfcF.get(tag).use { Pair(it.maxTransceiveLength, it.timeout) }
            tag.techList.contains(NfcV::class.java.name) -> NfcV.get(tag).use { Pair(it.maxTransceiveLength, 0) }
            tag.techList.contains(IsoDep::class.java.name) -> IsoDep.get(tag)
                .use { Pair(it.maxTransceiveLength, it.timeout) }
            else -> Pair(0, 0)
        }
        return Triple(allTagList, maxTransceiveLength, timeout)
    }

    private fun getIcManufacturerName(tag: Tag): String {
        val tagId = tag.id
        return if (tagId.toHex().startsWith("5f")) {
            Log.d(TAG, "getIcManufacturerName: Nordic SemiConductor")
            "Nordic SemiConductor"
        } else "Other company"
    }

    private fun onMifareClassicTagDiscovered(tag: Tag, generalTagInformation: GeneralTagInformation) {
        val mifareClassic = MifareClassic.get(tag)
        mifareClassic?.let {
            mifareClassic.connect()
            val sectorCount = mifareClassic.sectorCount
            val mifareClassicTagType = mifareClassic.type
            val mifareClassicTagSize = mifareClassic.size
            Log.d(
                TAG, "onTagDiscovered: Number of sector $sectorCount " +
                        "\ntype: ${MifareClassicTagType.getTagType(mifareClassicTagType)} " +
                        "\nSize ${mifareClassic.size} " +
                        "\nnumber of blocks in each sector: ${mifareClassic.getBlockCountInSector(2)} " +
                        "\nBlock count: ${mifareClassic.blockCount}"
            )
//            for (i in sectorCount)
            val a = mifareClassic.getBlockCountInSector(2)
//            val sector = mifareClassic.blockToSector()
            val mifareClassicMessage = MifareClassicMessage(
               sectorCount = sectorCount,
               tagType = MifareClassicTagType.getTagType(mifareClassicTagType),
               tagSize = mifareClassicTagSize,
               blockCount = mifareClassic.blockCount,
            )
            val mifareClassicTag = MifareClassicTag(generalTagInformation, mifareClassicMessage)

            _nfcScanningState.value = _nfcScanningState.value.copy(tag = mifareClassicTag )
            //                    mifareClassic.readBlock()
            mifareClassic.close()
        }
    }

    private fun onNdefTagDiscovered(it: Tag, generalTagInformation: GeneralTagInformation) {
        val ndef = Ndef.get(it)
        ndef?.let {
            ndef.connect()
            val message = ndef.ndefMessage
            ndef.cachedNdefMessage
            message?.let { ndefMessage ->
                val ndefRecords = ndefMessage.records.map {
                    NdefRecord(
                        typeNameFormat = TnfNameFormatter.getTnfName(it.tnf.toInt()),
                        type = String(it.type),
                        payloadLength = it.payload.size,
                        payloadData = String(it.payload),
                    )
                }

                val ndefTag = NdefTag(
                    general = generalTagInformation,
                    nfcNdefMessage = NfcNdefMessage(
                        recordCount = ndefMessage.records.size,
                        currentMessageSize = ndefMessage.byteArrayLength,
                        maximumMessageSize = ndef.maxSize,
                        isNdefWritable = ndef.isWritable,
                        ndefRecord = ndefRecords,
                        ndefType = ndef.type
                    )
                )
                _nfcScanningState.value = _nfcScanningState.value.copy(tag = ndefTag)
            }
            ndef.close()
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        // Disable ReaderMode
        if (nfcAdapter != null) nfcAdapter?.disableReaderMode(owner as Activity)
    }
}
