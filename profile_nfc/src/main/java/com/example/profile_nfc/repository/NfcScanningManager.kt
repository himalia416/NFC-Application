package com.example.profile_nfc.repository

import android.app.Activity
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.nfc.tech.NfcA
import android.nfc.tech.NfcB
import android.nfc.tech.NfcF
import android.nfc.tech.NfcV
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.domain.nfcTag.GeneralTagInformation
import com.example.domain.nfcTag.NfcTag
import com.example.domain.nfcTag.OtherTag
import com.example.profile_nfc.data.NfcTech.ISPDEP
import com.example.profile_nfc.data.NfcTech.MIFARE_CLASSIC
import com.example.profile_nfc.data.NfcTech.NDEF
import com.example.profile_nfc.data.NfcTech.NFCA
import com.example.profile_nfc.data.NfcTech.NFCB
import com.example.profile_nfc.data.NfcTech.NFCF
import com.example.profile_nfc.data.NfcTech.NFCV
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
                val allTags = getAllTagList(it)
                val maxTransceiveLength = getMaxTransceiveLength(it)
                val transceiveTimeOut = getTransceiveTimeOut(it)

                scope.launch {
                    val generalTagInformation = GeneralTagInformation(
                        serialNumber = it.id.toHex(),
                        tagTechnology = allTags,
                        icManufacturerName = getIcManufacturerName(getIdentifier(tag)),
                        maxTransceiveLength = maxTransceiveLength,
                        transceiveTimeout = transceiveTimeOut.toString()
                    )
                    when {
                        NDEF in it.techList -> {
                            val ndef = OnNdefTagDiscovered.parse(it, generalTagInformation)
                            _nfcScanningState.value = _nfcScanningState.value.copy(tag = ndef)
                        }

                        MIFARE_CLASSIC in it.techList -> {
                            val mifare = OnMifareTagDiscovered.parse(it, generalTagInformation)
                            _nfcScanningState.value = _nfcScanningState.value.copy(tag = mifare)
                        }
//                        it.techList.contains(IsoDep::class.java.name) -> {onIsoDepTagDiscovered(it, generalTagInformation)}
                        else -> {
                            onOtherTagDiscovered(generalTagInformation)
                        }
                    }
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Tag disconnected. Reason: " + e.message)
        }
    }

    private fun getIdentifier(tag: Tag) = tag.id.toHex().subSequence(0, 2).toString()

    /**
     * Return the maximum number of bytes that can be sent with transceive.
     * Returns: the maximum number of bytes that can be sent with transceive.
     */
    private fun getMaxTransceiveLength(tag: Tag): Int? {
        val tagTechList = tag.techList
        return when {
            NFCA in tagTechList -> NfcA.get(tag).use { it.maxTransceiveLength }
            NFCB in tagTechList -> NfcB.get(tag).use { it.maxTransceiveLength }
            NFCF in tagTechList -> NfcF.get(tag).use { it.maxTransceiveLength }
            NFCV in tagTechList -> NfcV.get(tag).use { it.maxTransceiveLength }
            ISPDEP in tagTechList -> IsoDep.get(tag).use { it.maxTransceiveLength }
            else -> null
        }
    }

    /**
     * Get the current transceive timeout in milliseconds.
     * Returns: timeout value in milliseconds
     */
    private fun getTransceiveTimeOut(tag: Tag): Int? {
        val tagTechList = tag.techList
        return when{
            NFCA in tagTechList -> NfcA.get(tag).use { it.timeout }
            NFCF in tagTechList -> NfcF.get(tag).use { it.timeout }
            ISPDEP in tagTechList -> IsoDep.get(tag).use { it.timeout }
            else -> null
        }
    }

    /**
     * Returns technologies available in this tag
     */
    private fun getAllTagList(tag: Tag): List<String> =  tag.techList.map { it.split('.').last() }

    /**
     * Get Ic Manufacturer Name.
     */
    private suspend fun getIcManufacturerName(identifier: String): String {
        return manufacturerNameRepository.getManufacturerName(identifier)?.company
            ?: "Company not found"
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
