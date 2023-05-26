package no.nordicsemi.profile_nfc.repository

import android.nfc.Tag
import android.nfc.tech.Ndef
import no.nordicsemi.domain.nfcTag.GeneralTagInformation
import no.nordicsemi.domain.nfcTag.NdefTag
import no.nordicsemi.domain.nfcTag.ndef.NdefRecord
import no.nordicsemi.domain.nfcTag.ndef.NdefTagType.Companion.getTagType
import no.nordicsemi.domain.nfcTag.ndef.NfcNdefMessage
import no.nordicsemi.domain.nfcTag.ndef.record.mapper.NdefRecordTypeMapper

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
