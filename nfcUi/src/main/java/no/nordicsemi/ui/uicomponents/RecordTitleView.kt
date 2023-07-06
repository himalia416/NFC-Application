package no.nordicsemi.ui.uicomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.TextFormat
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.nfcui.R

@Composable
fun RecordTitleView(
    recordTitle: String,
    index: Int,
    modifier: Modifier = Modifier,
    recordIcon: ImageVector? = null,
    isExpanded: Boolean = false,
    onExpandClicked: () -> Unit
) {
    val expandIcon = if (isExpanded) Icons.Default.ExpandMore else Icons.Default.ExpandLess
    Column {
        if (index > 0) Divider(thickness = 1.dp)
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.clickable { onExpandClicked() }
        ) {
            recordIcon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = modifier
                )
            }
            Text(
                text = stringResource(
                    id = R.string.record_name,
                    index + 1,
                    recordTitle
                ),
                modifier = Modifier.weight(1f)
            )
            Icon(imageVector = expandIcon,
                contentDescription = null,
                modifier = modifier.clickable { onExpandClicked() }
            )
        }
        if (isExpanded) Divider(thickness = 1.dp)
    }
}

@Preview
@Composable
fun RecordTitleViewPreview() {
    NordicTheme {
        RecordTitleView(
            recordTitle = "Text Record",
            index = 0,
            modifier = Modifier.padding(8.dp),
            recordIcon = Icons.Default.TextFormat,
            isExpanded = true,
            onExpandClicked = {}
        )
    }
}