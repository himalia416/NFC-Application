package no.nordicsemi.ui.viewmodel

import no.nordicsemi.domain.nfcTag.miFareClassic.MifareClassicMessage
import no.nordicsemi.domain.nfcTag.ndef.NfcNdefMessage
import no.nordicsemi.domain.nfcTag.nfcA.NfcAInfo
import no.nordicsemi.domain.nfcTag.nfcB.NfcBInfo
import no.nordicsemi.domain.nfcTag.nfcF.NfcFInfo
import no.nordicsemi.domain.nfcTag.nfcV.NfcVInfo

data class NfcUiState(
    val serialNumber: String = "",
    val manufacturerName: String = "",
    val techList: List<String> = emptyList(),
    val availableTagTechnology: NfcTagState? = null,
)

data class NfcTagState(
    val nfcAInfo: NfcAInfo? = null,
    val nfcBInfo: NfcBInfo? = null,
    val nfcFInfo: NfcFInfo? = null,
    val nfcVInfo: NfcVInfo? = null,
    val nfcNdefMessage: NfcNdefMessage? = null,
    val mifareClassicMessage: MifareClassicMessage? = null,
)
