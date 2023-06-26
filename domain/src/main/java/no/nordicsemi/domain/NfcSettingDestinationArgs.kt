package no.nordicsemi.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import no.nordicsemi.domain.nfcTag.DiscoveredTag

@Parcelize
sealed interface NfcSettingDestinationArgs : Parcelable

@Parcelize
data class NfcSettingsWithTag(
    val tag: DiscoveredTag
) : NfcSettingDestinationArgs

@Parcelize
object NfcSettingNoTag : NfcSettingDestinationArgs