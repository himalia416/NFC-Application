package no.nordicsemi.domain.nfcTag

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GeneralTagInformation(
    val serialNumber: String = "",
    val availableTagTechnologies: List<String> = emptyList(),
    var nfcAInfo: NfcAInfo? = null,
    var nfcBInfo: NfcBInfo? = null,
    var nfcFInfo: NfcFInfo? = null,
    var nfcVInfo: NfcVInfo? = null,
) : Parcelable

@Parcelize
data class NfcAInfo(
    val atqa: String,
    val sak: String,
    val maxTransceiveLength: Int,
    val transceiveTimeout: Int,
) : Parcelable

@Parcelize
data class NfcBInfo(
    val applicationData: String,
    val protocolInfo: String,
    val maxTransceiveLength: Int,
) : Parcelable

@Parcelize
data class NfcFInfo(
    val manufacturerByte: String,
    val systemCode: String,
    val maxTransceiveLength: Int,
    val transceiveTimeout: Int
) : Parcelable

@Parcelize
data class NfcVInfo(
    val dsfId: String,
    val responseFlags: String,
    val maxTransceiveLength: Int,
) : Parcelable
