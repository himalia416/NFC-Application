package no.nordicsemi.domain.nfcTag.ndef.record.type.externaltype

internal enum class TnfExternalType(val type: ByteArray) {
    RTD_ANDROID_APP("android.com:pkg".toByteArray())
}
