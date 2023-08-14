package no.nordicsemi.nfc.settings.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import no.nordicsemi.nfc.domain.nfcTag.DiscoveredTag

@Parcelize
sealed interface NfcSettingDestinationArgs : Parcelable

@Parcelize
data class NfcSettingsWithTag(
    val tag: DiscoveredTag
) : NfcSettingDestinationArgs

@Parcelize
object NfcSettingNoTag : NfcSettingDestinationArgs