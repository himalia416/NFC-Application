package no.nordicsemi.nfcscanner.repository

import android.app.Activity
import android.nfc.NfcAdapter
import android.nfc.Tag
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.nordicsemi.domain.nfcTag.NfcTag
import no.nordicsemi.nfcscanner.data.NfcFlags
import no.nordicsemi.nfcscanner.data.NfcTech.MIFARE_CLASSIC
import no.nordicsemi.nfcscanner.data.NfcTech.NDEF
import no.nordicsemi.nfcscanner.data.NfcTech.NFCA
import no.nordicsemi.nfcscanner.data.NfcTech.NFCB
import no.nordicsemi.nfcscanner.data.NfcTech.NFCF
import no.nordicsemi.nfcscanner.data.NfcTech.NFCV
import no.nordicsemi.nfcscanner.utility.toHex
import no.nordicsemi.remotedatabase.domain.ManufacturerNameRepository
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

data class NfcScanningState(
    val isScanning: Boolean = false,
    val tag: NfcTag? = null,
)

@HiltViewModel
class NfcScanningManagerVM @Inject constructor(
    private val nfcScanningManager: NfcScanningManager
) : ViewModel() {

    fun onResume(activity: Activity) {
        nfcScanningManager.onResume(activity)
    }

    fun onPause(activity: Activity) {
        nfcScanningManager.onPause(activity)
    }
}

@Singleton
class NfcScanningManager @Inject constructor(
    private val manufacturerNameRepository: ManufacturerNameRepository,
    private val nfcAdapter: NfcAdapter?,
    private val scope: CoroutineScope,
) {
    private val _nfcScanningState: MutableStateFlow<NfcScanningState> =
        MutableStateFlow(NfcScanningState())
    val nfcScanningState = _nfcScanningState.asStateFlow()
    private val _icManufacturerName: MutableStateFlow<String> = MutableStateFlow("")
    val icManufacturerName = _icManufacturerName.asStateFlow()
    private val _serialNumber: MutableStateFlow<String> = MutableStateFlow("")
    val serialNumber = _serialNumber.asStateFlow()
    private val _techList: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val techList = _techList.asStateFlow()

    fun onResume(activity: Activity) {
        nfcAdapter?.let {
            if (it.isEnabled) {
                val readerFlags =
                    enumValues<NfcFlags>().fold(0) { acc, flag -> acc or flag.value }
                // Enable ReaderMode for all types of card and disable platform sounds
                it.enableReaderMode(activity, ::onTagDiscovered, readerFlags, null)
            }
        }
    }

    var job: Job? = null

    private fun onTagDiscovered(tag: Tag?) {
        // Are we already processing a Tag
        if (_nfcScanningState.value.isScanning) {
            // Ignore this Tag
            return
        }
        job?.cancel()
        _icManufacturerName.value = "Loading..."
        try {
            tag?.let {
                getTagInfo(it)
                job = scope.launch {
                    _icManufacturerName.emit(getIcManufacturerName(getIdentifier(it)))
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * Provides the discovered tag information.
     */
    private fun getTagInfo(tag: Tag) {
        _nfcScanningState.value = _nfcScanningState.value.copy(isScanning = true)
        _serialNumber.value = tag.id.toHex()
        _techList.value = getAllTagList(tag)

        val nfcTag = NfcTag(
            nfcAInfo = tag.techList.find { it == NFCA }?.let { OnNfcATagDiscovered.parse(tag) },
            nfcBInfo = tag.techList.find { it == NFCB }?.let { OnNfcBTagDiscovered.parse(tag) },
            nfcFInfo = tag.techList.find { it == NFCF }?.let { OnNfcFTagDiscovered.parse(tag) },
            nfcVInfo = tag.techList.find { it == NFCV }?.let { OnNfcVTagDiscovered.parse(tag) },
            nfcNdefMessage = tag.techList.find { it == NDEF }
                ?.let { OnNdefTagDiscovered.parse(tag) },
            mifareClassicField = tag.techList.find { it == MIFARE_CLASSIC }
                ?.let { OnMifareTagDiscovered.parse(tag) }
        )
        _nfcScanningState.value = _nfcScanningState.value.copy(tag = nfcTag)
        _nfcScanningState.value = _nfcScanningState.value.copy(isScanning = false)
    }

    private fun getIdentifier(tag: Tag) = tag.id.toHex().subSequence(0, 2).toString()

    /**
     * Returns technologies available in this tag
     */
    private fun getAllTagList(tag: Tag): List<String> = tag.techList.map { it.split('.').last() }

    /**
     * Returns Ic Manufacturer Name.
     */
    private suspend fun getIcManufacturerName(identifier: String): String {
        return manufacturerNameRepository.getManufacturerName(identifier)?.company
            ?: "Company not found"
    }

    fun onPause(activity: Activity) {
        // Disable ReaderMode
        nfcAdapter?.disableReaderMode(activity)
    }
}
