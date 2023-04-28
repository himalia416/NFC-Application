package com.example.profile_nfc.scanning.views

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.profile_nfc.scanning.data.GeneralTagInformation
import com.example.profile_nfc.scanning.views.tagViews.ndefTag.TagInfoView

@Composable
fun MifareClassicTagView(
    generalTagInfo: GeneralTagInformation
) {
    LazyColumn {
        item { TagInfoView(generalTagInfo) }
    }
}