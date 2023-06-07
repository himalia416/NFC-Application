package no.nordicsemi.domain.nfcTag.ndef.record.mapper

import android.nfc.NdefRecord
import no.nordicsemi.domain.nfcTag.ndef.TnfNameFormatter
import no.nordicsemi.domain.nfcTag.ndef.record.NdefRecordType
import no.nordicsemi.domain.nfcTag.ndef.record.type.absoluteuri.AbsoluteUriParser
import no.nordicsemi.domain.nfcTag.ndef.record.type.externaltype.AndroidPackageRecordParser
import no.nordicsemi.domain.nfcTag.ndef.record.type.externaltype.ExternalTypeRecord
import no.nordicsemi.domain.nfcTag.ndef.record.type.externaltype.TnfExternalType
import no.nordicsemi.domain.nfcTag.ndef.record.type.mimetype.MimeTypeParser
import no.nordicsemi.domain.nfcTag.ndef.record.type.wellknowntype.SmartPosterRecordParser
import no.nordicsemi.domain.nfcTag.ndef.record.type.wellknowntype.TextRecordParser
import no.nordicsemi.domain.nfcTag.ndef.record.type.wellknowntype.TnfWellKnown
import no.nordicsemi.domain.nfcTag.ndef.record.type.wellknowntype.UriRecordParser
import no.nordicsemi.domain.nfcTag.ndef.record.type.wellknowntype.handover.AlternativeCarrierRecordParser
import no.nordicsemi.domain.nfcTag.ndef.record.type.wellknowntype.handover.HandoverCarrierRecordParser
import no.nordicsemi.domain.nfcTag.ndef.record.type.wellknowntype.handover.HandoverReceiveRecordParser
import no.nordicsemi.domain.nfcTag.ndef.record.type.wellknowntype.handover.HandoverSelectRecordParser

// The RTD type name format is specified in NFCForum-TS-RTD_1.0.
object NdefRecordTypeMapper {

    fun getNdefRecordType(record: NdefRecord): NdefRecordType {
        val type = record.type

        return when (TnfNameFormatter.getTnfName(record.tnf.toInt())) {
            TnfNameFormatter.TNF_WELL_KNOWN.tnf -> when {
                type.contentEquals(TnfWellKnown.RTD_TEXT.type) -> TextRecordParser.parse(record)
                type.contentEquals(TnfWellKnown.RTD_URI.type) -> UriRecordParser.parse(record)
                type.contentEquals(TnfWellKnown.RTD_SMART_POSTER.type) -> SmartPosterRecordParser.parse(record)
                type.contentEquals(TnfWellKnown.RTD_ALTERNATIVE_CARRIER.type) -> AlternativeCarrierRecordParser.parse(record)
                type.contentEquals(TnfWellKnown.RTD_HANDOVER_CARRIER.type) -> HandoverCarrierRecordParser.parse(record)
                type.contentEquals(TnfWellKnown.RTD_HANDOVER_REQUEST.type) -> HandoverReceiveRecordParser.parse(record)
                type.contentEquals(TnfWellKnown.RTD_HANDOVER_SELECT.type) -> HandoverSelectRecordParser.parse(record)
                else -> throw IllegalArgumentException("Unknown Record Type")
            }

            TnfNameFormatter.TNF_EXTERNAL_TYPE.tnf -> when {
                type.contentEquals(TnfExternalType.RTD_ANDROID_APP.type) -> AndroidPackageRecordParser.parse(record)
                else -> ExternalTypeRecord.parse(record)
            }

            TnfNameFormatter.TNF_ABSOLUTE_URI.tnf -> AbsoluteUriParser.parse(record)

            TnfNameFormatter.TNF_MIME_MEDIA.tnf -> MimeTypeParser.parse(record)

            else -> throw IllegalArgumentException("Unknown Record Type")
        }
    }
}
