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
import no.nordicsemi.domain.nfcTag.GeneralTagInformation
import no.nordicsemi.profile_nfc.R
import no.nordicsemi.profile_nfc.component.RowInCardView
import no.nordicsemi.profile_nfc.component.TitleWithIcon
import no.nordicsemi.profile_nfc.utility.toSerialNumber
import no.nordicsemi.android.common.theme.NordicTheme

@Composable
fun TagInfoView(generalTagInfo: GeneralTagInformation) {
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
                    secondItem = generalTagInfo.icManufacturerName
                )
                RowInCardView(
                    firstItem = stringResource(id = R.string.serial_number),
                    secondItem = generalTagInfo.serialNumber.toSerialNumber()
                )
                generalTagInfo.maxTransceiveLength?.let {
                    RowInCardView(
                        firstItem = stringResource(id = R.string.maximum_transceive_len),
                        secondItem = stringResource(
                            id = R.string.bytes,
                            it.toString()
                        )
                    )
                }
                generalTagInfo.transceiveTimeout?.let {
                    RowInCardView(
                        firstItem = stringResource(id = R.string.transceive_time_out),
                        secondItem = stringResource(
                            id = R.string.millisecond,
                            it
                        )
                    )
                }
                Text(
                    text = stringResource(id = R.string.available_tag_technologies),
                    style = MaterialTheme.typography.titleMedium
                )
                generalTagInfo.tagTechnology.onEach {
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
                tagTechnology = listOf("NFCA", "NFCF"),
                tagType = "Type2",
            )
        )
    }
}