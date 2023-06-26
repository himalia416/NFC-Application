package no.nordicsemi.settings.viewmodel

import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import no.nordicsemi.android.common.navigation.Navigator
import no.nordicsemi.android.common.navigation.viewmodel.SimpleNavigationViewModel
import no.nordicsemi.domain.NfcSettingNoTag
import no.nordicsemi.domain.NfcSettingsWithTag
import no.nordicsemi.domain.nfcTag.DiscoveredTag
import no.nordicsemi.serialization.domain.NfcJsonAdapter
import no.nordicsemi.settings.NfcSettingScreenId
import no.nordicsemi.settings.views.NavigateUp
import no.nordicsemi.settings.views.OnAboutAppClick
import no.nordicsemi.settings.views.OnAboutNfcClick
import no.nordicsemi.settings.views.OnEmailClick
import no.nordicsemi.settings.views.OnExportScanResultClick
import no.nordicsemi.settings.views.OnImportScanClick
import no.nordicsemi.settings.views.OnPlaySoundClick
import no.nordicsemi.settings.views.OnScanHistoryClick
import no.nordicsemi.settings.views.OnVibrateClick
import no.nordicsemi.settings.views.SettingsScreenViewEvent
import no.nordicsemi.settingsstorage.domain.NFCSettings
import no.nordicsemi.settingsstorage.repository.SettingsRepository
import no.nordicsemi.welcome.NfcWelcomeDestinationId
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

private val NFC_INFOCENTER_LINK =
    Uri.parse("https://infocenter.nordicsemi.com/topic/sdk_nrf5_v17.1.0/examples_nfc.html")

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    repository: SettingsRepository,
    private val navigator: Navigator,
    savedStateHandle: SavedStateHandle,
    private val jsonAdapter: NfcJsonAdapter,
    @ApplicationContext private val context: Context,
) : SimpleNavigationViewModel(navigator, savedStateHandle) {
    private val TAG =  "NfcSettings"
    val state = repository.settings.stateIn(viewModelScope, SharingStarted.Eagerly, NFCSettings())
    private val _exportState = MutableStateFlow<ExportState>(ExportStateUnknown)
    val exportState = _exportState.asStateFlow()
    var nfcTag: DiscoveredTag? = null
    var isTagAvailable: Boolean = true // todo put it in the repository

    private val tag = parameterOf(NfcSettingScreenId)
    init {
        when (val dTag = tag){
            NfcSettingNoTag -> {
                isTagAvailable = false
            }
            is NfcSettingsWithTag -> {
                nfcTag = dTag.tag

            }
        }
    }

    fun onEvent(event: SettingsScreenViewEvent) {
        when (event) {
            is OnImportScanClick -> importScanResult()
            is OnExportScanResultClick -> { _exportState.value = ExportStarted }
            is OnScanHistoryClick -> showScanHistory()
            is OnEmailClick -> shareScanResultInEmail()
            is OnPlaySoundClick -> onPlaySoundClick()
            is OnVibrateClick -> onVibrationClick()
            is OnAboutNfcClick -> open(NFC_INFOCENTER_LINK)
            is OnAboutAppClick -> { navigator.navigateTo(NfcWelcomeDestinationId) }
            is NavigateUp -> navigator.navigateUp()
        }
    }

    private fun onVibrationClick() {
        TODO("Not yet implemented")
    }

    private fun onPlaySoundClick() {
        TODO("Not yet implemented")
    }
    private fun showScanHistory() {
        TODO("Not yet implemented")
    }

    fun export(contentResolver: ContentResolver, uri: Uri) {
        try {
            viewModelScope.launch {
                val data = nfcTag?.let { jsonAdapter.toJson(it).toByteArray() }
                contentResolver.openOutputStream(uri)?.run {
                    write(data)
                    close()
                }
            }
            _exportState.value = ExportSuccess
        } catch (e: Exception) {
            _exportState.value = ErrorInExport(e)
        }
    }

    private fun shareScanResultInEmail() {
        try {
            val jsonData = nfcTag?.let { jsonAdapter.toJson(it) }!!
            val uri = createJsonFile(context, jsonData)
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "application/json"
                putExtra(Intent.EXTRA_SUBJECT, "JSON Data")
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            viewModelScope.launch {
                context.startActivities(arrayOf(intent))
            }
        } catch (e: ActivityNotFoundException) {
            Log.e(TAG, "ActivityNotFoundException: $e")
        } catch (t: Throwable) {
            Log.e(TAG, "Throwable: $t")
        }
    }

    @Throws(IOException::class)
    private fun createJsonFile(context: Context, jsonData: String): Uri {
        val file = File(context.externalCacheDir, "json_data.json")
        FileOutputStream(file).use {
            it.write(jsonData.toByteArray())
        }
        return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    }

    private fun importScanResult() {
        TODO("Not yet implemented")
    }

}