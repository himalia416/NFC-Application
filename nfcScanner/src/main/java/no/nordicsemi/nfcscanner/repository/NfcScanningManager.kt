package no.nordicsemi.nfcscanner.repository

import android.app.Activity
import android.nfc.NfcAdapter
import android.nfc.Tag
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.nordicsemi.domain.nfcTag.NfcTag
import no.nordicsemi.nfcscanner.data.NfcFlags
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
    private val _nfcScanningState: MutableStateFlow<NfcScanningState> =
        MutableStateFlow(NfcScanningState())
    val nfcScanningState = _nfcScanningState.asStateFlow()
    private val _icManufacturerName: MutableStateFlow<String> = MutableStateFlow("")
    val icManufacturerName = _icManufacturerName.asStateFlow()
    private val _serialNumber: MutableStateFlow<String> = MutableStateFlow("")
    val serialNumber = _serialNumber.asStateFlow()
    private val _techList: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val techList = _techList.asStateFlow()

    override fun onResume(owner: LifecycleOwner) {
        nfcAdapter?.let {
            _nfcScanningState.value = _nfcScanningState.value.copy(isNfcSupported = true)
            if (it.isEnabled) {
                _nfcScanningState.value = _nfcScanningState.value.copy(isNfcEnabled = true)
                val readerFlags =
                    enumValues<NfcFlags>().fold(0) { acc, flag -> acc or flag.value }
                // Enable ReaderMode for all types of card and disable platform sounds
                it.enableReaderMode(owner as Activity, ::onTagDiscovered, readerFlags, null)
            }
        }
    }

    var job: Job? = null

    private fun onTagDiscovered(tag: Tag?) {
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
        _serialNumber.value = tag.id.toHex()
        _techList.value = getAllTagList(tag)

        val nfcAInfo = tag.techList.find { it == NFCA }?.let { OnNfcATagDiscovered.parse(tag) }
        val nfcBInfo = tag.techList.find { it == NFCB }?.let { OnNfcBTagDiscovered.parse(tag) }
        val nfcFInfo = tag.techList.find { it == NFCF }?.let { OnNfcFTagDiscovered.parse(tag) }
        val nfcVInfo = tag.techList.find { it == NFCV }?.let { OnNfcVTagDiscovered.parse(tag) }
        val ndef = tag.techList.find { it == NDEF }?.let { OnNdefTagDiscovered.parse(tag) }
        // For the NXP card, will use Taplinx api
        // The use of this api, follow here:
        // https://github.com/dfpalomar/TapLinxSample/blob/master/src/main/java/com/nxp/sampletaplinx/MainActivity.java
        val mifare = tag.techList.find { it == NDEF }?.let { OnMifareTagDiscovered.parse(tag) }
        val nfcTag = NfcTag(
            nfcAInfo = nfcAInfo,
            nfcBInfo = nfcBInfo,
            nfcFInfo = nfcFInfo,
            nfcVInfo = nfcVInfo,
            nfcNdefMessage = ndef,
            mifareClassicField = mifare
        )
        _nfcScanningState.value = _nfcScanningState.value.copy(tag = nfcTag)
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

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        // Disable ReaderMode
        nfcAdapter?.disableReaderMode(owner as Activity)
    }
}