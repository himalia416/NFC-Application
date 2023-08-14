package no.nordicsemi.nfc.domain.nfcTag.nfcF

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NfcFInfo(
    val manufacturerByte: String,
    val systemCode: String,
    val maxTransceiveLength: Int,
    val transceiveTimeout: Int
) : Parcelable