package no.nordicsemi.domain.nfcTag.ndef.record.type.wellknowntype

import android.nfc.NdefRecord

internal object ActionRecordParser {

    fun parseRecommendedAction(records: NdefRecord): RecommendedAction {
        val record = getByType(records)
        return if (record == null) {
            RecommendedAction.UNKNOWN
        } else {
            val action = record.payload[0]
            RecommendedAction.LOOKUP[action] ?: RecommendedAction.UNKNOWN
        }
    }

    fun parseType(record: NdefRecord): String? {
        val type = getByType(record)
        return type?.type?.let { String(it) }
    }

    private fun getByType(records: NdefRecord): NdefRecord? {
        return if (records.type.contentEquals(ACTION_RECORD_TYPE)) {
            records
        } else null
    }

    private val ACTION_RECORD_TYPE =
        byteArrayOf('a'.code.toByte(), 'c'.code.toByte(), 't'.code.toByte())
}

enum class RecommendedAction(val action: Byte) {
    UNKNOWN(-1),
    DO_ACTION(0),
    SAVE_FOR_LATER(1),
    OPEN_FOR_EDITING(2);

    companion object {
        val LOOKUP = values().associateBy(RecommendedAction::action)
    }
}

object ActionRecordMapper {
    fun mapper(action: RecommendedAction) {
        when (action) {
            RecommendedAction.UNKNOWN -> TODO()
            RecommendedAction.DO_ACTION -> TODO()
            RecommendedAction.SAVE_FOR_LATER -> TODO()
            RecommendedAction.OPEN_FOR_EDITING -> TODO()
        }
    }
}

