package no.nordicsemi.domain.nfcTag.nfcV

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NfcVInfo(
    val dsfId: String,
    val responseFlags: String,
    val maxTransceiveLength: Int,
) : Parcelable