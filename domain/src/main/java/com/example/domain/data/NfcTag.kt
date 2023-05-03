package com.example.domain.data

sealed interface NfcTag {
    val general: GeneralTagInformation
}

data class NdefTag(
    override val general: GeneralTagInformation,
    val nfcNdefMessage: NfcNdefMessage
) : NfcTag

data class MifareClassicTag(
    override val general: GeneralTagInformation,
    val mifareClassicField1: MifareClassicMessage
) : NfcTag

data class OtherTag(
    override val general: GeneralTagInformation,
//    val otherTag: OtherTagMessage,
) : NfcTag