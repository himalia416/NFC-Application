package no.nordicsemi.nfc.domain.nfcTag.nfcB

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NfcBInfo(
    val applicationData: String,
    val protocolInfo: String,
    val maxTransceiveLength: Int,
) : Parcelable