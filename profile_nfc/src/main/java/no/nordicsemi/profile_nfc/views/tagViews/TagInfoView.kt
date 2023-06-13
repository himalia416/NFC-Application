package no.nordicsemi.profile_nfc.views.tagViews

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.domain.nfcTag.GeneralTagInformation
import no.nordicsemi.profile_nfc.R
import no.nordicsemi.profile_nfc.component.RowInCardView
import no.nordicsemi.profile_nfc.component.TitleWithIcon
import no.nordicsemi.profile_nfc.utility.toSerialNumber

@Composable
fun TagInfoView(
    generalTagInfo: GeneralTagInformation,
    manufacturerName: String
) {
    var expanded by rememberSaveable { mutableStateOf(true) }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column {
            TitleWithIcon(
                icon = painterResource(id = R.drawable.information),
                title = stringResource(id = R.string.tag_info),
                modifier = Modifier.padding(8.dp),
                textStyle = MaterialTheme.typography.headlineSmall,
                isExpanded = expanded,
                onExpandClicked = { expanded = !expanded }
            )

            AnimatedVisibility(
                visible = expanded,
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    RowInCardView(
                        title = stringResource(id = R.string.ic_manufacturer),
                        description = manufacturerName,
                    )
                    RowInCardView(
                        title = stringResource(id = R.string.serial_number),
                        description = generalTagInfo.serialNumber.toSerialNumber()
                    )
                    generalTagInfo.nfcAInfo?.let {
                        RowInCardView(
                            title = stringResource(id = R.string.atqa),
                            description = it.atqa,
                            tooltipText = stringResource(id = R.string.atqa_des)
                        )
                        RowInCardView(
                            title = stringResource(id = R.string.sak),
                            description = it.sak,
                            tooltipText = stringResource(id = R.string.sak_des)
                        )
                        RowInCardView(
                            title = stringResource(id = R.string.maximum_transceive_len),
                            description = stringResource(
                                id = R.string.bytes,
                                it.maxTransceiveLength.toString()
                            )
                        )
                        RowInCardView(
                            title = stringResource(id = R.string.transceive_time_out),
                            description = stringResource(
                                id = R.string.millisecond,
                                it.transceiveTimeout.toString()
                            )
                        )
                    }
                    generalTagInfo.nfcBInfo?.let {
                        RowInCardView(
                            title = stringResource(id = R.string.application_data),
                            description = it.applicationData
                        )
                        RowInCardView(
                            title = stringResource(id = R.string.nfcB_protocol_information),
                            description = it.protocolInfo
                        )
                        RowInCardView(
                            title = stringResource(id = R.string.maximum_transceive_len),
                            description = stringResource(
                                id = R.string.bytes,
                                it.maxTransceiveLength.toString()
                            )
                        )
                    }
                    generalTagInfo.nfcFInfo?.let {
                        RowInCardView(
                            title = stringResource(id = R.string.nfcF_manufacturer_byte),
                            description = it.manufacturerByte
                        )
                        RowInCardView(
                            title = stringResource(id = R.string.nfcF_system_code),
                            description = it.systemCode
                        )
                        RowInCardView(
                            title = stringResource(id = R.string.maximum_transceive_len),
                            description = stringResource(
                                id = R.string.bytes,
                                it.maxTransceiveLength.toString()
                            )
                        )
                        RowInCardView(
                            title = stringResource(id = R.string.transceive_time_out),
                            description = stringResource(
                                id = R.string.millisecond,
                                it.transceiveTimeout.toString()
                            )
                        )
                    }
                    generalTagInfo.nfcVInfo?.let {
                        RowInCardView(
                            title = stringResource(id = R.string.nfcV_dsf_id),
                            description = it.dsfId
                        )
                        RowInCardView(
                            title = stringResource(id = R.string.nfcV_response_flags),
                            description = it.responseFlags
                        )
                        RowInCardView(
                            title = stringResource(id = R.string.maximum_transceive_len),
                            description = stringResource(
                                id = R.string.bytes,
                                it.maxTransceiveLength.toString()
                            )
                        )
                    }
                    ShowAvailableTechnologies(list = generalTagInfo.availableTagTechnologies)
                }
            }
        }
    }
}

@Preview
@Composable
fun TagInfoViewPreview() {
    NordicTheme {
        TagInfoView(
            generalTagInfo = GeneralTagInformation(
                serialNumber = "2345ca8709",
                availableTagTechnologies = listOf("NFCA", "NFCF"),
            ),
            manufacturerName = "Nordic Semiconductor ASA"
        )
    }
}

@Composable
fun ShowAvailableTechnologies(list: List<String>) {
    Column {
        Text(
            text = stringResource(id = R.string.available_tag_technologies),
            style = MaterialTheme.typography.titleMedium
        )
        list.onEach {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall
            )
        }

    }
}

@Preview
@Composable
fun Preview() {
    NordicTheme {
        ShowAvailableTechnologies(list = listOf("NfcA, Ndef,"))
    }
}