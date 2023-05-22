package com.example.profile_nfc.repository

import android.nfc.Tag
import android.nfc.tech.Ndef
import com.example.domain.data.GeneralTagInformation
import com.example.domain.data.NdefRecord
import com.example.domain.data.NdefTag
import com.example.domain.data.NdefTagType.Companion.getTagType
import com.example.domain.data.NfcNdefMessage
import com.example.domain.data.TnfNameFormatter

object NdefTagParser {
    fun parse(tag: Tag, generalTagInformation: GeneralTagInformation): NdefTag? {
        val ndef = Ndef.get(tag)
        ndef?.let {
            ndef.connect()
            val message = ndef.ndefMessage
            message?.let { ndefMessage ->
                val ndefRecords = ndefMessage.records.map { record ->
                    NdefRecord(
                        typeNameFormat = TnfNameFormatter.getTnfName(record.tnf.toInt()),
                        type = String(record.type),
                        payloadLength = record.payload.size,
                        payloadData = record.payload
                    )
                }

                val nfcNdefMessage = NfcNdefMessage(
                    recordCount = ndefMessage.records.size,
                    currentMessageSize = ndefMessage.byteArrayLength,
                    maximumMessageSize = ndef.maxSize,
                    isNdefWritable = ndef.isWritable,
                    ndefRecord = ndefRecords,
                    ndefType = getTagType(ndef.type)
                )

                return NdefTag(general = generalTagInformation, nfcNdefMessage = nfcNdefMessage)
            }
        }

        return null
    }
}
