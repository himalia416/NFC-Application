package com.example.nfcapplication.viewmodel

sealed class NfcState

object ScanNfcTag: NfcState()

object NfcNotEnabled : NfcState()

object NfcNotSupported : NfcState()

object NfcTagDiscovered : NfcState ()

object EnableNfc : NfcState()