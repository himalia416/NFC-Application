package com.example.domain.nfcTag

import android.os.Parcelable
import com.example.domain.nfcTag.miFareClassic.MifareClassicMessage
import com.example.domain.nfcTag.ndef.NfcNdefMessage
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface NfcTag : Parcelable {
    val general: GeneralTagInformation
}

@Parcelize
data class NdefTag(
    override val general: GeneralTagInformation,
    val nfcNdefMessage: NfcNdefMessage
) : NfcTag

@Parcelize
data class MifareClassicTag(
    override val general: GeneralTagInformation,
    val mifareClassicField1: MifareClassicMessage
) : NfcTag

@Parcelize
data class OtherTag(
    override val general: GeneralTagInformation,
//    val otherTag: OtherTagMessage,
) : NfcTag