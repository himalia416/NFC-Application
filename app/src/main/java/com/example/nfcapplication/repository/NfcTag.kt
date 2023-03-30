package com.example.nfcapplication.repository

import com.example.nfcapplication.data.GeneralTagInformation
import com.example.nfcapplication.data.NfcNdefMessage

sealed interface NfcTag {
    val general: GeneralTagInformation
}

data class NdefTag(
    override val general: GeneralTagInformation,
    val nfcNdefMessage: NfcNdefMessage
) : NfcTag

data class MifareClassicTag(
    override val general: GeneralTagInformation,
    val mifareClassicField1: NfcNdefMessage
) : NfcTag
