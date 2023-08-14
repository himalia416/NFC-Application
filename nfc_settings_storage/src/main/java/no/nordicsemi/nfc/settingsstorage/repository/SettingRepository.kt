package no.nordicsemi.nfc.settingsstorage.repository

import dagger.hilt.android.scopes.ViewModelScoped
import no.nordicsemi.nfc.settingsstorage.domain.NFCSettings
import javax.inject.Inject

@ViewModelScoped
class SettingsRepository @Inject constructor(
    private val settingsDataSource: SettingsDataSource,
) {
    val settings = settingsDataSource.settings

    suspend fun storeSettings(settings: NFCSettings) {
        settingsDataSource.storeSettings(settings)
    }

    suspend fun tickWelcomeScreenShown() {
        settingsDataSource.tickWelcomeScreenShown()
    }
}