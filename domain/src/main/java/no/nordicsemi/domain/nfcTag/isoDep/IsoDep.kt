package no.nordicsemi.domain.nfcTag.isoDep

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IsoDepInfo(
    val hiLayerResponse: String,
    val getHistoricalBytes: String,
    val isExtendedLengthApduSupported: Boolean,

) : Parcelable
