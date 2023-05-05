package com.example.setting.views

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.example.setting.R

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ExportScreen(
    onExportClicked: (Uri) -> Unit,
) {

    val currentTime = System.currentTimeMillis()

    val createDocument = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument(stringResource(R.string.document_type)),
        onResult = { uri -> uri?.let { onExportClicked(it) } })

    LaunchedEffect(key1 = Unit) {
        createDocument.launch("nfc-scan-result-$currentTime.json")
    }
}
/*

@Composable
fun ImportScreen(
    onImportClicked: () -> Unit
){
    Text(text = "Select a file from document")
}*/
