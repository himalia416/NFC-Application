package no.nordicsemi.nfc.domain.nfcTag

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import no.nordicsemi.nfc.domain.nfcTag.miFareClassic.MifareClassicMessage
import no.nordicsemi.nfc.domain.nfcTag.ndef.NfcNdefMessage
import no.nordicsemi.nfc.domain.nfcTag.nfcA.NfcAInfo
import no.nordicsemi.nfc.domain.nfcTag.nfcB.NfcBInfo
import no.nordicsemi.nfc.domain.nfcTag.nfcF.NfcFInfo
import no.nordicsemi.nfc.domain.nfcTag.nfcV.NfcVInfo

@Parcelize
data class DiscoveredTag(
    val serialNumber: String = "",
    val manufacturerName: String = "",
    val techList: List<String> = emptyList(),
    val availableTagTechnology: NfcTag
) : Parcelable

@Parcelize
data class NfcTag(
    val nfcNdefMessage: NfcNdefMessage? = null,
    val mifareClassicField: MifareClassicMessage? = null,
    val nfcAInfo: NfcAInfo? = null,
    val nfcBInfo: NfcBInfo? = null,
    val nfcFInfo: NfcFInfo? = null,
    val nfcVInfo: NfcVInfo? = null,
) : Parcelable
