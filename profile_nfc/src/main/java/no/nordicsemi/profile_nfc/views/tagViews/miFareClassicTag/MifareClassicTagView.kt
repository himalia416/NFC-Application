package no.nordicsemi.profile_nfc.views.tagViews.miFareClassicTag

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import no.nordicsemi.domain.nfcTag.GeneralTagInformation
import no.nordicsemi.profile_nfc.views.tagViews.TagInfoView

@Composable
fun MifareClassicTagView(
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