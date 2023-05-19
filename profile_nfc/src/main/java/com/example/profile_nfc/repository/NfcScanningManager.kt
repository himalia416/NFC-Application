package com.example.profile_nfc.repository

import android.app.Activity
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.nfc.tech.MifareClassic
import android.nfc.tech.Ndef
import android.nfc.tech.NfcA
import android.nfc.tech.NfcB
import android.nfc.tech.NfcF
import android.nfc.tech.NfcV
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.domain.data.GeneralTagInformation
import com.example.domain.data.MifareClassicMessage
import com.example.domain.data.MifareClassicTag
import com.example.domain.data.MifareClassicTagType
import com.example.domain.data.NdefRecord
import com.example.domain.data.NdefTag
import com.example.domain.data.NdefTagType.Companion.getTagType
import com.example.domain.data.NfcNdefMessage
import com.example.domain.data.NfcTag
import com.example.domain.data.OtherTag
import com.example.domain.data.TnfNameFormatter
import com.example.profile_nfc.data.ReaderFlag
import com.example.profile_nfc.utility.toHex
import com.example.remotedatabase.domain.ManufacturerNameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

data class NfcScanningState(
    val isNfcSupported: Boolean = false,
    val isNfcEnabled: Boolean = false,
    val tag: NfcTag? = null,
)

data class TagInfo(
    val allTags: List<String> = emptyList(),
    val maxTransceiveLength: Int = 0,
    val transceiveTimeOut: Int = 0,
)

@Singleton
class NfcScanningManager @Inject constructor(
    private val manufacturerNameRepository: ManufacturerNameRepository,
    private val nfcAdapter: NfcAdapter?,
    private val scope: CoroutineScope,
) : DefaultLifecycleObserver {
    private val TAG = NfcScanningManager::class.java.simpleName
    private val _nfcScanningState: MutableStateFlow<NfcScanningState> =
        MutableStateFlow(NfcScanningState())
    val nfcScanningState = _nfcScanningState.asStateFlow()

    override fun onResume(owner: LifecycleOwner) {
        nfcAdapter?.let {
            _nfcScanningState.value = _nfcScanningState.value.copy(isNfcSupported = true)
            if (it.isEnabled) {
                _nfcScanningState.value = _nfcScanningState.value.copy(isNfcEnabled = true)
                val readerFlags =
                    enumValues<ReaderFlag>().fold(0) { acc, flag -> acc or flag.value }
                // Enable ReaderMode for all types of card and disable platform sounds
                it.enableReaderMode(owner as Activity, ::onTagDiscovered, readerFlags, null)
            }
        }
    }

    private fun onTagDiscovered(tag: Tag?) {
        try {
            tag?.let {
                val (allTags, maxTransceiveLength, transceiveTimeOut) = getAllTagAndTransceiveLength(tag)
                scope.launch {
                    val generalTagInformation = GeneralTagInformation(
                        serialNumber = it.id.toHex(),
                        tagTechnology = allTags,
                        icManufacturerName = getIcManufacturerName(getIdentifier(tag)),
                        maxTransceiveLength = maxTransceiveLength,
                        transceiveTimeout = transceiveTimeOut.toString()
                    )
                    when {
                        it.techList.contains(Ndef::class.java.name) -> { onNdefTagDiscovered(it, generalTagInformation) }
                        it.techList.contains(MifareClassic::class.java.name) -> { onMifareClassicTagDiscovered(it, generalTagInformation) }
                        else -> { onOtherTagDiscovered(generalTagInformation) }
                    }
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Tag disconnected. Reason: " + e.message)
        }
    }

    private fun getIdentifier(tag: Tag) = tag.id.toHex().subSequence(0, 2).toString()

    private fun getAllTagAndTransceiveLength(tag: Tag): TagInfo {
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
        return TagInfo(allTagList, maxTransceiveLength, timeout)
    }

    private suspend fun getIcManufacturerName(identifier: String): String {
        return manufacturerNameRepository.getManufacturerName(identifier)?.company ?: "Company not found"
    }

    private fun onMifareClassicTagDiscovered(
        tag: Tag,
        generalTagInformation: GeneralTagInformation
    ) {
        val mifareClassic = MifareClassic.get(tag)
        mifareClassic?.let {
            mifareClassic.connect()
            val sectorCount = mifareClassic.sectorCount
            val mifareClassicTagType = mifareClassic.type
            val mifareClassicTagSize = mifareClassic.size
            Log.d(TAG, "onMifareClassicTagDiscovered: serial number: ${tag.id.toHex()}")
            Log.d(
                TAG, "onTagDiscovered: Number of sector $sectorCount " +
                        "\ntype: ${MifareClassicTagType.getTagType(mifareClassicTagType)} " +
                        "\nSize ${mifareClassic.size} " +
                        "\nnumber of blocks in each sector: ${mifareClassic.getBlockCountInSector(2)} " +
                        "\nBlock count: ${mifareClassic.blockCount}"
            )
            val mifareClassicMessage = MifareClassicMessage(
                sectorCount = sectorCount,
                tagType = MifareClassicTagType.getTagType(mifareClassicTagType),
                tagSize = mifareClassicTagSize,
                blockCount = mifareClassic.blockCount,
            )
            val mifareClassicTag = MifareClassicTag(generalTagInformation, mifareClassicMessage)

            _nfcScanningState.value = _nfcScanningState.value.copy(tag = mifareClassicTag)
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
                val ndefRecords = ndefMessage.records.map { record ->
                    Log.d(TAG, "Uri Protocol field: ${record.payload[0]}")
                    NdefRecord(
                        typeNameFormat = TnfNameFormatter.getTnfName(record.tnf.toInt()),
                        type = String(record.type),
                        payloadLength = record.payload.size,
                        payloadData = record.payload,
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
                        ndefType = getTagType(ndef.type)
                    )
                )
                _nfcScanningState.value = _nfcScanningState.value.copy(tag = ndefTag)
            }
            ndef.close()
        }
    }

    private fun onOtherTagDiscovered(generalTagInformation: GeneralTagInformation) {
        val otherTag = OtherTag(generalTagInformation)
        _nfcScanningState.value = _nfcScanningState.value.copy(tag = otherTag)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        // Disable ReaderMode
        nfcAdapter?.disableReaderMode(owner as Activity)
    }
}