package no.nordicsemi.nfc.settings.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.HearingDisabled
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Nfc
import androidx.compose.material.icons.filled.StayPrimaryPortrait
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.android.common.theme.view.NordicAppBar
import no.nordicsemi.nfc.settings.BuildConfig
import no.nordicsemi.nfc.settings.R
import no.nordicsemi.nfc.settings.viewmodel.ErrorInExport
import no.nordicsemi.nfc.settings.viewmodel.ExportStarted
import no.nordicsemi.nfc.settings.viewmodel.ExportStateUnknown
import no.nordicsemi.nfc.settings.viewmodel.ExportSuccess
import no.nordicsemi.nfc.settings.viewmodel.SettingsViewModel

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    val viewModel: SettingsViewModel = hiltViewModel()
    val onEvent: (SettingsScreenViewEvent) -> Unit = { viewModel.onEvent(it) }

    Column {
        NordicAppBar(
            text = stringResource(id = R.string.settings),
            onNavigationButtonClick = { onEvent(NavigateUp) },
        )
        val state by viewModel.state.collectAsStateWithLifecycle()
        val exportState by viewModel.exportState.collectAsState()
        val resolver = LocalContext.current.contentResolver

        when (val es = exportState) {
            ExportStarted -> ExportScreen(onExportClicked = { viewModel.export(resolver, it) })
            ExportSuccess -> {}
            is ErrorInExport -> {}
            ExportStateUnknown -> {}
        }

        LazyColumn {
            item {
                Headline(stringResource(id = R.string.scan_report))

                SettingsButtonWithIcon(
                    title = stringResource(id = R.string.export_scan),
                    enabled = viewModel.isTagAvailable,
                    description = stringResource(id = R.string.export_scan_des),
                    icon = Icons.Filled.Download,
                    onClick = { onEvent(OnExportScanResultClick) }

                )
                SettingsButtonWithIcon(
                    title = stringResource(id = R.string.import_scan),
                    description = stringResource(id = R.string.import_scan_des),
                    icon = Icons.Filled.Upload,
                    onClick = { /*TODO*/ },
                    enabled = false
                )

                SettingsButtonWithIcon(
                    title = stringResource(id = R.string.clear_scan),
                    description = stringResource(id = R.string.clear_scan_des),
                    icon = Icons.Filled.ClearAll,
                    onClick = { /*TODO*/ },
                    enabled = false
                )

                SettingsButtonWithIcon(
                    title = stringResource(id = R.string.scan_history),
                    description = stringResource(id = R.string.scan_history_des),
                    icon = Icons.Filled.History,
                    onClick = {/*TODO*/ },
                    enabled = false
//                    onClick = { onEvent(OnScanHistoryClick) }
                )

                Spacer(modifier = Modifier.size(16.dp))
                Headline(stringResource(id = R.string.share_scan_report))
                SettingsButtonWithIcon(
                    title = stringResource(id = R.string.email),
                    description = stringResource(id = R.string.attach_email_des),
                    icon = Icons.Filled.Email,
                    onClick = { onEvent(OnEmailClick) }
                )

                Spacer(modifier = Modifier.size(16.dp))
                Headline(stringResource(id = R.string.user_interface))
                SettingsButtonWithIcon(
                    title = stringResource(id = R.string.play_sound),
                    description = stringResource(id = R.string.play_sound_des),
                    icon = Icons.Filled.VolumeOff,
                    onEnabledIcon = Icons.Filled.VolumeUp,
                    onClick = {/*TODO*/ },
                    enabled = false
//                    onClick = { onEvent(OnPlaySoundClick) }
                )

                SettingsButtonWithIcon(
                    title = stringResource(id = R.string.vibrate),
                    description = stringResource(id = R.string.vibrate_des),
                    icon = Icons.Filled.Vibration,
                    onEnabledIcon = Icons.Filled.HearingDisabled,
                    onClick = {/*TODO*/ },
                    enabled = false
//                    onClick = { onEvent(OnVibrateClick) }
                )

                Spacer(modifier = Modifier.size(16.dp))
                Headline(stringResource(id = R.string.other))
                SettingsButtonWithIcon(
                    title = stringResource(id = R.string.about_nfc),
                    description = stringResource(id = R.string.about_nfc_des),
                    icon = Icons.Filled.Nfc,
                    onClick = { onEvent(OnAboutNfcClick) }
                )
                SettingsButtonWithIcon(
                    title = stringResource(id = R.string.about_nfc_app),
                    description = stringResource(id = R.string.help_des),
                    icon = Icons.Filled.Help,
                    onClick = { onEvent(OnAboutAppClick) }
                )

                SettingsButtonWithIcon(
                    title = stringResource(id = R.string.version),
                    description = BuildConfig.VERSION_NAME,
                    icon = Icons.Filled.StayPrimaryPortrait,
                    onClick = { /*TODO*/ }
                )
                Spacer(modifier = Modifier.size(16.dp))
            }

        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Preview
@Composable
fun SettingsScreenPreview() {
    NordicTheme {
        SettingsScreen()
    }
}

@Composable
private fun SettingsButtonWithIcon(
    title: String,
    description: String? = null,
    icon: ImageVector?,
    onClick: () -> Unit,
    onEnabledIcon: ImageVector? = null,
    enabled: Boolean = true
) {
    val color = if (enabled) {
        LocalContentColor.current
    } else {
        LocalContentColor.current.copy(alpha = 0.38f)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(enabled = enabled) { onClick() }
            .fillMaxWidth()
    ) {
        icon?.let {
            onEnabledIcon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.padding(16.dp),
                    tint = color,
                )
            } ?: run {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.padding(16.dp),
                    tint = color,
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = color,
            )

            description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = color,
                )
            }
        }
    }
}

@Preview
@Composable
fun SettingsButtonWithIconPreview() {
    NordicTheme {
        SettingsButtonWithIcon(
            title = stringResource(id = R.string.import_scan),
            description = stringResource(id = R.string.import_scan_des),
            icon = Icons.Filled.Download,
            onClick = {}
        )
    }
}

@Composable
private fun Headline(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        modifier = Modifier.padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.secondary
    )
}

@Preview
@Composable
fun HeadlinePreview() {
    NordicTheme {
        Headline(stringResource(id = R.string.share_scan_report))
    }
}