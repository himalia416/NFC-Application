package no.nordicsemi.profile_nfc.views.tagViews.otherTags

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import no.nordicsemi.domain.nfcTag.GeneralTagInformation
import no.nordicsemi.profile_nfc.views.tagViews.ndefTag.TagInfoView
import no.nordicsemi.android.common.theme.NordicTheme

@Composable
fun OtherTagView(
    generalTagInfo: GeneralTagInformation
) {
    LazyColumn {
        item { TagInfoView(generalTagInfo) }
    }
}

@Preview
@Composable
fun OtherTagViewPreview() {
    NordicTheme {
        OtherTagView(
            generalTagInfo = GeneralTagInformation(
                serialNumber = "2345ca8709",
                tagTechnology = listOf("NFCA", "NFCF"),
                tagType = "Type2",
            )
        )
    }
}