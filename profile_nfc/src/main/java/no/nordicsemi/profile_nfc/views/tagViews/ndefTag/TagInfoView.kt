package no.nordicsemi.profile_nfc.views.tagViews.ndefTag

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    Column {
        TitleWithIcon(
            icon = painterResource(id = R.drawable.information),
            title = stringResource(id = R.string.tag_info),
            modifier = Modifier.padding(8.dp),
            textStyle = MaterialTheme.typography.headlineSmall
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                RowInCardView(
                    firstItem = stringResource(id = R.string.ic_manufacturer),
                    secondItem = manufacturerName
                )
                RowInCardView(
                    firstItem = stringResource(id = R.string.serial_number),
                    secondItem = generalTagInfo.serialNumber.toSerialNumber()
                )
                generalTagInfo.nfcAInfo?.let {
                    RowInCardView(
                        firstItem = stringResource(id = R.string.atqa),
                        secondItem = it.atqa
                    )
                    RowInCardView(
                        firstItem = stringResource(id = R.string.sak),
                        secondItem = it.sak
                    )
                    RowInCardView(
                        firstItem = stringResource(id = R.string.maximum_transceive_len),
                        secondItem = stringResource(
                            id = R.string.bytes,
                            it.maxTransceiveLength.toString()
                        )
                    )
                    RowInCardView(
                        firstItem = stringResource(id = R.string.transceive_time_out),
                        secondItem = stringResource(
                            id = R.string.millisecond,
                            it.transceiveTimeout.toString()
                        )
                    )
                }
                generalTagInfo.nfcBInfo?.let {
                    RowInCardView(
                        firstItem = stringResource(id = R.string.application_data),
                        secondItem = it.applicationData
                    )
                    RowInCardView(
                        firstItem = stringResource(id = R.string.nfcB_protocol_information),
                        secondItem = it.protocolInfo
                    )
                    RowInCardView(
                        firstItem = stringResource(id = R.string.maximum_transceive_len),
                        secondItem = stringResource(
                            id = R.string.bytes,
                            it.maxTransceiveLength.toString()
                        )
                    )
                }
                generalTagInfo.nfcFInfo?.let {
                    RowInCardView(
                        firstItem = stringResource(id = R.string.nfcF_manufacturer_byte),
                        secondItem = it.manufacturerByte
                    )
                    RowInCardView(
                        firstItem = stringResource(id = R.string.nfcF_system_code),
                        secondItem = it.systemCode
                    )
                    RowInCardView(
                        firstItem = stringResource(id = R.string.maximum_transceive_len),
                        secondItem = stringResource(
                            id = R.string.bytes,
                            it.maxTransceiveLength.toString()
                        )
                    )
                    RowInCardView(
                        firstItem = stringResource(id = R.string.transceive_time_out),
                        secondItem = stringResource(
                            id = R.string.millisecond,
                            it.transceiveTimeout.toString()
                        )
                    )
                }
                generalTagInfo.nfcVInfo?.let {
                    RowInCardView(
                        firstItem = stringResource(id = R.string.nfcV_dsf_id),
                        secondItem = it.dsfId
                    )
                    RowInCardView(
                        firstItem = stringResource(id = R.string.nfcV_response_flags),
                        secondItem = it.responseFlags
                    )
                    RowInCardView(
                        firstItem = stringResource(id = R.string.maximum_transceive_len),
                        secondItem = stringResource(
                            id = R.string.bytes,
                            it.maxTransceiveLength.toString()
                        )
                    )
                }
                Text(
                    text = stringResource(id = R.string.available_tag_technologies),
                    style = MaterialTheme.typography.titleMedium
                )
                generalTagInfo.availableTagTechnologies.onEach {
                    Text(text = it)
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