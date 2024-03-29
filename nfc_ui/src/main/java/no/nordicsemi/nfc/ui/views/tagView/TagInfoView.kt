package no.nordicsemi.nfc.ui.views.tagView

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
import no.nordicsemi.nfc.domain.nfcTag.nfcA.NfcAInfo
import no.nordicsemi.nfc.domain.nfcTag.nfcB.NfcBInfo
import no.nordicsemi.nfc.domain.nfcTag.nfcF.NfcFInfo
import no.nordicsemi.nfc.domain.nfcTag.nfcV.NfcVInfo
import no.nordicsemi.nfc.ui.R
import no.nordicsemi.nfc.ui.uicomponents.NfcRowView
import no.nordicsemi.nfc.ui.uicomponents.TitleView
import no.nordicsemi.nfc.utils.toSerialNumber
import no.nordicsemi.nfc.ui.uicomponents.NfcListView


@Composable
fun TagInfoView(
    id: String,
    manufacturerName: String,
    techList: List<String>,
    nfcAInfo: NfcAInfo?,
    nfcBInfo: NfcBInfo?,
    nfcFInfo: NfcFInfo?,
    nfcVInfo: NfcVInfo?,
) {
    var expanded by rememberSaveable { mutableStateOf(true) }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column {
            TitleView(
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
                    NfcRowView(
                        title = stringResource(id = R.string.ic_manufacturer),
                        description = manufacturerName,
                    )
                    NfcRowView(
                        title = stringResource(id = R.string.serial_number),
                        description = id.toSerialNumber()
                    )
                    nfcAInfo?.let {
                        NfcRowView(
                            title = stringResource(id = R.string.atqa),
                            description = it.atqa,
                            tooltipText = stringResource(id = R.string.atqa_des)
                        )
                        NfcRowView(
                            title = stringResource(id = R.string.sak),
                            description = it.sak,
                            tooltipText = stringResource(id = R.string.sak_des)
                        )
                        NfcRowView(
                            title = stringResource(id = R.string.maximum_transceive_len),
                            description = stringResource(
                                id = R.string.bytes,
                                it.maxTransceiveLength.toString()
                            )
                        )
                        NfcRowView(
                            title = stringResource(id = R.string.transceive_time_out),
                            description = stringResource(
                                id = R.string.millisecond,
                                it.transceiveTimeout.toString()
                            )
                        )
                    }
                    nfcBInfo?.let {
                        NfcRowView(
                            title = stringResource(id = R.string.application_data),
                            description = it.applicationData
                        )
                        NfcRowView(
                            title = stringResource(id = R.string.nfcB_protocol_information),
                            description = it.protocolInfo
                        )
                        NfcRowView(
                            title = stringResource(id = R.string.maximum_transceive_len),
                            description = stringResource(
                                id = R.string.bytes,
                                it.maxTransceiveLength.toString()
                            )
                        )
                    }
                    nfcFInfo?.let {
                        NfcRowView(
                            title = stringResource(id = R.string.nfcF_manufacturer_byte),
                            description = it.manufacturerByte
                        )
                        NfcRowView(
                            title = stringResource(id = R.string.nfcF_system_code),
                            description = it.systemCode
                        )
                        NfcRowView(
                            title = stringResource(id = R.string.maximum_transceive_len),
                            description = stringResource(
                                id = R.string.bytes,
                                it.maxTransceiveLength.toString()
                            )
                        )
                        NfcRowView(
                            title = stringResource(id = R.string.transceive_time_out),
                            description = stringResource(
                                id = R.string.millisecond,
                                it.transceiveTimeout.toString()
                            )
                        )
                    }
                    nfcVInfo?.let {
                        NfcRowView(
                            title = stringResource(id = R.string.nfcV_dsf_id),
                            description = it.dsfId
                        )
                        NfcRowView(
                            title = stringResource(id = R.string.nfcV_response_flags),
                            description = it.responseFlags
                        )
                        NfcRowView(
                            title = stringResource(id = R.string.maximum_transceive_len),
                            description = stringResource(
                                id = R.string.bytes,
                                it.maxTransceiveLength.toString()
                            )
                        )
                    }
                    NfcListView(
                        title = stringResource(id = R.string.available_tag_technologies),
                        list = techList
                    )
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
            id = "2345ca8709",
            manufacturerName = "Nordic Semiconductor ASA",
            techList = listOf("NFCA", "NFCF"),
            nfcAInfo = null,
            nfcBInfo = null,
            nfcFInfo = null,
            nfcVInfo = null,
        )
    }
}
