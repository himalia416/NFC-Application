package no.nordicsemi.nfc.scanner.data

import android.nfc.tech.IsoDep
import android.nfc.tech.MifareClassic
import android.nfc.tech.MifareUltralight
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.nfc.tech.NfcA
import android.nfc.tech.NfcB
import android.nfc.tech.NfcBarcode
import android.nfc.tech.NfcF
import android.nfc.tech.NfcV

object NfcTech {
    val NFCA = NfcA::class.java.name
    val NFCB = NfcB::class.java.name
    val NFCF = NfcF::class.java.name
    val NFCV = NfcV::class.java.name
    val ISPDEP = IsoDep::class.java.name
    val NDEF = Ndef::class.java.name
    val NDEF_FORMATABLE = NdefFormatable::class.java.name
    val NFC_BARCODE = NfcBarcode::class.java.name
    val ISO_DEP: Class<IsoDep> = IsoDep::class.java
    val MIFARE_CLASSIC = MifareClassic::class.java.name
    val MIFARE_ULTRALIGHT = MifareUltralight::class.java.name
}