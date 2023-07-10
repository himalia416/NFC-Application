package no.nordicsemi.domain.nfcTag.nfcA

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NfcAInfo(
    val atqa: String,
    val sak: String,
    val maxTransceiveLength: Int,
    val transceiveTimeout: Int,
) : Parcelable