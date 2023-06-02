package no.nordicsemi.profile_nfc.views.tagViews.otherTags

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import no.nordicsemi.domain.nfcTag.GeneralTagInformation
import no.nordicsemi.profile_nfc.views.tagViews.ndefTag.TagInfoView
import no.nordicsemi.android.common.theme.NordicTheme

@Composable
fun OtherTagView(
    generalTagInfo: GeneralTagInformation,
    manufacturerName: String
) {
    LazyColumn {
        item {
            TagInfoView(
                generalTagInfo = generalTagInfo,
                manufacturerName = manufacturerName
            )
        }
    }
}

@Preview
@Composable
fun OtherTagViewPreview() {
    NordicTheme {
        OtherTagView(
            generalTagInfo = GeneralTagInformation(
                serialNumber = "2345ca8709",
                availableTagTechnologies = listOf("NFCA", "NFCF"),
            ),
            manufacturerName = "Nordic Semiconductor ASA"
        )
    }
}