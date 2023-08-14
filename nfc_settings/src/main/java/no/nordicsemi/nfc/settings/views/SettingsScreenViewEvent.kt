package no.nordicsemi.nfc.settings.views

sealed interface SettingsScreenViewEvent

internal object OnImportScanClick : SettingsScreenViewEvent

internal object OnExportScanResultClick: SettingsScreenViewEvent

internal object OnScanHistoryClick: SettingsScreenViewEvent

internal object OnEmailClick : SettingsScreenViewEvent

internal object OnPlaySoundClick : SettingsScreenViewEvent

internal object OnVibrateClick : SettingsScreenViewEvent

internal object OnAboutNfcClick : SettingsScreenViewEvent

internal object OnAboutAppClick : SettingsScreenViewEvent

internal object NavigateUp : SettingsScreenViewEvent