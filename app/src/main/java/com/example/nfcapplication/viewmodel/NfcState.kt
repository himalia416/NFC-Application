package com.example.nfcapplication.viewmodel

sealed class NfcState

data class ShowWelcomeDialog(val isOkayClicked: Boolean) : NfcState()

//object ShowScanningPage : NfcState()

object ShowTagDiscoveredPage : NfcState()
