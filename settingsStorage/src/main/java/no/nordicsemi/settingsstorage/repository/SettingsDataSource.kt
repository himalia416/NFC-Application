package no.nordicsemi.settingsstorage.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import no.nordicsemi.settingsstorage.domain.NFCSettings
import javax.inject.Inject
import javax.inject.Singleton

private val PLAY_SOUND_KEY = booleanPreferencesKey("play_sound")
private val VIBRATION_KEY = booleanPreferencesKey("vibration")
private val SHOW_WELCOME_KEY = booleanPreferencesKey("show_welcome")
private val IMPORT_SCAN_RESULT_KEY = booleanPreferencesKey("import_scan")
private val SHOW_HISTORY_KEY = booleanPreferencesKey("show_history")

@Singleton
class SettingsDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    val settings = context.dataStore.data.map { it.toSettings() }

    suspend fun tickWelcomeScreenShown() {
        context.dataStore.edit {
            it[SHOW_WELCOME_KEY] = false
        }
    }

    suspend fun storeSettings(settings: NFCSettings) {
        context.dataStore.edit {
            it[PLAY_SOUND_KEY] = settings.playSound
            it[VIBRATION_KEY] = settings.vibration
            it[SHOW_WELCOME_KEY] = settings.showWelcomeScreen
            it[SHOW_HISTORY_KEY] = settings.showHistory
            it[IMPORT_SCAN_RESULT_KEY] = settings.importScanResult
        }
    }

    private fun Preferences.toSettings(): NFCSettings {
        return NFCSettings(
            this[PLAY_SOUND_KEY] ?: false,
            this[VIBRATION_KEY] ?: false,
            this[SHOW_WELCOME_KEY] ?: true,
            this[SHOW_HISTORY_KEY] ?: false,
            this[IMPORT_SCAN_RESULT_KEY] ?: false,
        )
    }
}