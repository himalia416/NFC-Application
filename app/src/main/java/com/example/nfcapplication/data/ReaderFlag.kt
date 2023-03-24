package com.example.nfcapplication.data

import android.nfc.NfcAdapter


enum class ReaderFlag(val value: Int) {
    NFC_A(NfcAdapter.FLAG_READER_NFC_A),
    NFC_B(NfcAdapter.FLAG_READER_NFC_B),
    NFC_F(NfcAdapter.FLAG_READER_NFC_F),
    NFC_V(NfcAdapter.FLAG_READER_NFC_V),
    NFC_BARCODE(NfcAdapter.FLAG_READER_NFC_BARCODE),
    NO_PLATFORM_SOUNDS(NfcAdapter.FLAG_READER_NO_PLATFORM_SOUNDS)
}