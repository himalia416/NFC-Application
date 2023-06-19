package no.nordicsemi.domain.nfcTag

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import no.nordicsemi.domain.nfcTag.miFareClassic.MifareClassicMessage
import no.nordicsemi.domain.nfcTag.ndef.NfcNdefMessage
import no.nordicsemi.domain.nfcTag.nfcA.NfcAInfo
import no.nordicsemi.domain.nfcTag.nfcB.NfcBInfo
import no.nordicsemi.domain.nfcTag.nfcF.NfcFInfo
import no.nordicsemi.domain.nfcTag.nfcV.NfcVInfo

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
