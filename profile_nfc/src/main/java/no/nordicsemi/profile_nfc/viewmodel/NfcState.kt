package no.nordicsemi.profile_nfc.viewmodel

internal enum class NfcState {
    ScanNfcTag,
    NfcNotEnabled,
    NfcNotSupported,
    NfcTagDiscovered,
    EnableNfc,
}