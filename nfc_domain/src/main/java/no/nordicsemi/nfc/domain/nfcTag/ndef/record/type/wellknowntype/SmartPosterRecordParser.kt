package no.nordicsemi.nfc.domain.nfcTag.ndef.record.type.wellknowntype

import android.nfc.FormatException
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import no.nordicsemi.nfc.domain.nfcTag.ndef.TnfNameFormatter
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.ActionRecord
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.SmartPoster
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.TextRecord
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.URIRecord
import no.nordicsemi.nfc.domain.nfcTag.ndef.record.type.absoluteuri.AbsoluteUriParser

internal object SmartPosterRecordParser {

    /**
     * NFC Forum Smart Poster Record Type Definition section 3.2.1.
     *
     * "The Title record for the service (there can be many of these in
     * different languages, but a language MUST NOT be repeated). This record is
     * optional."
     */
    private var textRecord: TextRecord? = null

    /**
     * NFC Forum Smart Poster Record Type Definition section 3.2.1.
     *
     * "The URI record. This is the core of the Smart Poster, and all other
     * records are just metadata about this record. There MUST be one URI record
     * and there MUST NOT be more than one."
     */
    private lateinit var uriRecord: URIRecord

    /**
     * NFC Forum Smart Poster Record Type Definition section 3.2.1.
     *
     * "The Action record. This record describes how the service should be
     * treated. For example, the action may indicate that the device should save
     * the URI as a bookmark or open a browser. The Action record is optional.
     * If it does not exist, the device may decide what to do with the service.
     * If the action record exists, it should be treated as a strong suggestion;
     * the UI designer may ignore it, but doing so will induce a different user
     * experience from device to device."
     */
    private var actionRecord: ActionRecord? = null

    /**
     * NFC Forum Smart Poster Record Type Definition section 3.2.1.
     *
     * "The Type record. If the URI references an external entity (e.g., via a
     * URL), the Type record may be used to declare the MIME type of the entity.
     * This can be used to tell the mobile device what kind of an object it can
     * expect before it opens the connection. The Type record is optional."
     */
    private var actionType: String? = null

    /**
     * Parse Smart Poster record.
     * The purpose of the Smart Poster RTD is to provide the necessary wrapper to fulfill the Smart Poster use case, as defined by the NFC Forum.
     * In the Smart Poster use case, information about an object, event, etc, is somehow attached onto a physical object.
     * The typical example is a movie poster which contains a tag with the Smart Poster record.
     * When the user touches it with his NFC-enabled device (such as a cell phone or a PDA), a browser window opens and the device connects to the Internet to fetch that data.
     * Another possibility might be that the device (if it’s a cell phone) sends an SMS to a number defined on the tag to access some value-added service.
     *
     * The design goal of the Smart Poster was to provide a simple way to access a remote service by using the touch paradigm.
     */
    fun parse(record: NdefRecord): SmartPoster {
        try {
            val subRecords = NdefMessage(record.payload)
            subRecords.records.map { ndefRecord ->
                val type = ndefRecord.type
                when (TnfNameFormatter.getTnfName(record.tnf.toInt())) {
                    TnfNameFormatter.TNF_WELL_KNOWN.tnf -> {
                        when {
                            type.contentEquals(TnfWellKnown.RTD_TEXT.type) -> {
                                textRecord = TextRecordParser.parse(ndefRecord)
                            }

                            type.contentEquals(TnfWellKnown.RTD_URI.type) -> {
                                uriRecord = UriRecordParser.parse(ndefRecord)
                            }

                            else -> {
                                val action = ActionRecordParser.parseRecommendedAction(ndefRecord)
                                ActionRecordMapper.mapper(action) // TODO: handle action record
                                actionType = ActionRecordParser.parseType(ndefRecord)
                            }
                        }
                    }

                    TnfNameFormatter.TNF_EXTERNAL_TYPE.tnf -> {
                        uriRecord = AbsoluteUriParser.parse(ndefRecord)
                    }
                }
            }
        } catch (e: FormatException) {
            throw IllegalArgumentException(e)
        }
        return SmartPoster(
            textRecord = textRecord,
            uriRecord = uriRecord,
            actionRecord = actionRecord
        )
    }
}
