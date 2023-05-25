package com.example.profile_nfc.repository

import android.nfc.Tag
import android.nfc.tech.Ndef
import com.example.domain.nfcTag.GeneralTagInformation
import com.example.domain.nfcTag.NdefTag
import com.example.domain.nfcTag.ndef.NdefRecord
import com.example.domain.nfcTag.ndef.NdefTagType.Companion.getTagType
import com.example.domain.nfcTag.ndef.NfcNdefMessage
import com.example.domain.nfcTag.ndef.record.mapper.NdefRecordTypeMapper

object OnNdefTagDiscovered {
    fun parse(tag: Tag, generalTagInformation: GeneralTagInformation): NdefTag? {
        val ndef = Ndef.get(tag)
        ndef?.let {
            ndef.connect()
            val message = ndef.ndefMessage
            message?.let { ndefMessage ->
                val ndefRecords = ndefMessage.records.map { record ->
                    NdefRecord(
                        recordType = NdefRecordTypeMapper.getNdefRecordType(record)
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
