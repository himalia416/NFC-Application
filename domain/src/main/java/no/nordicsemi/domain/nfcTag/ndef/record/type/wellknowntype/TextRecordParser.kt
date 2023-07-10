package no.nordicsemi.domain.nfcTag.ndef.record.type.wellknowntype

import android.nfc.NdefRecord
import no.nordisemi.utils.DataByteArray
import no.nordicsemi.domain.nfcTag.ndef.TnfNameFormatter
import no.nordicsemi.domain.nfcTag.ndef.record.TextRecord
import java.nio.charset.Charset

/**
 * Text Record.
 *
 * The NFC Forum Well Known Type  for the Text record is "T" (in NFC binary encoding: 0x54).
 * Text Record contains Status byte (1 byte), ISO/IANA language code (n bytes) and Text field (m bytes) which contains the actual text.
 * Encoding is either UTF-8 or UTF-16, depending on bit 7 of the status byte
 * and is determined by masking the status byte with the value 0x080 (0 = UTF-8, 1 = UTF-16).
 * The language code length is encoded in the six least significant bits of the status byte which is typically either two characters or five characters.
 * And the length of language field is determined by masking the status byte with the value 0x3F.
 */

internal object TextRecordParser {
    private const val LANGUAGE_CODE_MASK = 0x03F
    private const val TEXT_ENCODING_MASK = 0x80

    private val UTF8 = Charsets.UTF_8
    private val UTF16 = Charsets.UTF_16

    private fun getTextEncoding(statusByte: Int): Charset =
        if ((statusByte and TEXT_ENCODING_MASK) == 0) UTF8 else UTF16

    fun parse(record: NdefRecord): TextRecord {
        val typeNameFormat = TnfNameFormatter.getTnfName(record.tnf.toInt())

        val status = record.payload[0].toInt()
        val languageLength = status and LANGUAGE_CODE_MASK
        val textEncoding = getTextEncoding(status)
        val textLength = record.payload.size - 1 - languageLength
        val languageCode = String(record.payload, 1, languageLength, Charsets.US_ASCII)
        val actualText = String(record.payload, 1 + languageLength, textLength, textEncoding)

        return TextRecord(
            typeNameFormat = typeNameFormat,
            payloadType = "Text",
            payloadLength = record.payload.size,
            langCode = languageCode,
            encoding = textEncoding.toString(),
            actualText = actualText,
            payloadData = DataByteArray(record.payload)
        )
    }
}