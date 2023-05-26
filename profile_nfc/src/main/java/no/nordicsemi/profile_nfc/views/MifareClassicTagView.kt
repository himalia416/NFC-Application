package no.nordicsemi.profile_nfc.views

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import no.nordicsemi.domain.nfcTag.GeneralTagInformation
import no.nordicsemi.profile_nfc.views.tagViews.ndefTag.TagInfoView

@Composable
fun MifareClassicTagView(
    generalTagInfo: GeneralTagInformation
) {
    LazyColumn {
        item { TagInfoView(generalTagInfo) }
    }
}