package com.example.nfcapplication.viewmodel

sealed class NfcState

data class ShowWelcomeDialog(val isOkayClicked: Boolean) : NfcState()

object ScanNfcTag : NfcState()

object NfcNotEnabled : NfcState()