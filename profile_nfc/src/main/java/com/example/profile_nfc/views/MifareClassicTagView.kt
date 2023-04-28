package com.example.profile_nfc.views

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.profile_nfc.data.GeneralTagInformation
import com.example.profile_nfc.views.tagViews.ndefTag.TagInfoView

@Composable
fun MifareClassicTagView(
    generalTagInfo: GeneralTagInformation
) {
    LazyColumn {
        item { TagInfoView(generalTagInfo) }
    }
}