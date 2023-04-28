package com.example.nfcapplication.views

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.nfcapplication.data.GeneralTagInformation
import com.example.nfcapplication.views.tagViews.ndefTag.TagInfoView

@Composable
fun MifareClassicTagView(
    generalTagInfo: GeneralTagInformation
) {
    LazyColumn {
        item { TagInfoView(generalTagInfo) }
    }
}