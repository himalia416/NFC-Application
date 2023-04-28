package com.example.profile_nfc.settings.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Nfc
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.android.common.theme.view.NordicAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackNavigation: () -> Unit
) {
    Column {
        NordicAppBar(
            text = "Settings",
            onNavigationButtonClick = onBackNavigation,
        )
        LazyColumn {
            item {
                Headline("Scan Report")

                SettingsButtonWithIcon(
                    title = "Import Scan XML File",
                    description = "Import scan result locally in XML format.",
                    icon = Icons.Filled.Download,
                    onClick = { /*TODO*/ }
                )
                SettingsButtonWithIcon(
                    title = "Export Scan Result",
                    icon = Icons.Filled.Upload,
                    description = "Export previous scan result to view.",
                    onClick = { /*TODO*/ }
                )

                SettingsButtonWithIcon(
                    title = "Clear Scan Report",
                    description = "Clear exported scan report.",
                    icon = Icons.Filled.ClearAll,
                    onClick = { /*TODO*/ }
                )

                Spacer(modifier = Modifier.size(16.dp))
                Headline("Share Scan Report")
                SettingsButtonWithIcon(
                    title = "Email",
                    description = "Attach scan result in Email.",
                    icon = Icons.Filled.Email,
                    onClick = { /*TODO*/ }
                )

                Spacer(modifier = Modifier.size(16.dp))
                Headline("User Interface")
                SettingsButtonWithIcon(
                    title = "Play Sound",
                    description = "Turn on sound at the start or finish of scan.",
                    icon = Icons.Filled.VolumeOff,
                    onEnabledIcon = Icons.Filled.VolumeUp,
                    onClick = { /*TODO*/ }
                )

                SettingsButtonWithIcon(
                    title = "Vibrate",
                    description = "Vibrate when starting or finishing a scan.",
                    icon = Icons.Filled.Vibration,
                    onClick = { /*TODO*/ }
                )

                Spacer(modifier = Modifier.size(16.dp))
                Headline("Other")
                SettingsButtonWithIcon(
                    title = "About NFC",
                    description = "NFC Documentation on Nordic's Info-center.",
                    icon = Icons.Filled.Nfc,
                    onClick = { }
                )
                SettingsButtonWithIcon(
                    title = "Tutorial",
                    description = "Shows welcome screen.",
                    icon = Icons.Outlined.Info,
                    onClick = { /*TODO*/ }
                )

                SettingsButtonWithIcon(
                    title = "Version",
                    icon = null,
                    onClick = { /*TODO*/ }
                )
            }

        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    NordicTheme {
        SettingsScreen {}
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
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(enabled = enabled) { onClick() }
            .fillMaxWidth()
    ) {
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
        Spacer(Modifier.weight(1f))
        if (icon != null) {
            if (enabled) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.padding(16.dp)
                )
            } else onEnabledIcon?.let { Icon(imageVector = it, contentDescription = null) }
        }
    }
}

@Preview
@Composable
fun SettingsButtonPreview() {
    NordicTheme {
        SettingsButtonWithIcon(
            title = "Import Scan XML File",
            description = "Import scan result locally in XML format.",
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
fun HeadlinePreview(){
    NordicTheme {
        Headline("Share scan report")
    }
}