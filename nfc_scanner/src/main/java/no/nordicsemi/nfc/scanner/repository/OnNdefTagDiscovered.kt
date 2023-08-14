package no.nordicsemi.nfc.scanner.repository

import android.nfc.Tag
import android.nfc.tech.Ndef
import no.nordicsemi.nfc.domain.nfcTag.ndef.NdefTagType.Companion.getTagType
import no.nordicsemi.nfc.domain.nfcTag.ndef.NfcNdefMessage
import no.nordicsemi.nfc.domain.nfcTag.ndef.NfcNdefRecord
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.mapper.NdefRecordTypeMapper
import javax.inject.Inject

class OnNdefTagDiscovered @Inject constructor(
    private val recordParser: NdefRecordTypeMapper,
) {
    /**
     * Parses NdefMessage tag.
     */
    fun parse(tag: Tag): NfcNdefMessage? {
        val ndef = Ndef.get(tag) ?: return null
        ndef.connect()
        val ndefMessage = ndef.ndefMessage ?: return null
        val ndefRecords = ndefMessage.records.map { record ->
            NfcNdefRecord(record = recordParser.getNdefRecordType(record))
        }

        val nfcNdefMessage = NfcNdefMessage(
            recordCount = ndefMessage.records.size,
            currentMessageSize = ndefMessage.byteArrayLength,
            maximumMessageSize = ndef.maxSize,
            isNdefWritable = ndef.isWritable,
            ndefRecord = ndefRecords,
            ndefType = getTagType(ndef.type)
        )
        ndef.close()

        return nfcNdefMessage
    }
}
