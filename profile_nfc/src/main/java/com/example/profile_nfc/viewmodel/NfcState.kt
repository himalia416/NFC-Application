package com.example.profile_nfc.viewmodel

sealed class NfcState

internal object ScanNfcTag: NfcState()

internal object NfcNotEnabled : NfcState()

internal object NfcNotSupported : NfcState()

internal object NfcTagDiscovered : NfcState()

internal object EnableNfc : NfcState()