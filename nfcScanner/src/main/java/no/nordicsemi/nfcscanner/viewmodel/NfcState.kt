package no.nordicsemi.nfcscanner.viewmodel

internal enum class NfcState {
    ScanNfcTag,
    NfcNotEnabled,
    NfcNotSupported,
    NfcTagDiscovered,
    EnableNfc,
}