package no.nordicsemi.nfc.ui.uicomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.nfc.ui.R

@Composable
fun TitleView(
    icon: Painter,
    title: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    isExpanded: Boolean? = false,
    onExpandClicked: () -> Unit = {}
) {
    val expandIcon = if (isExpanded == true) Icons.Default.ExpandMore
    else Icons.Default.ExpandLess

    Column {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.clickable { onExpandClicked() }
        ) {
            Image(
                painter = icon,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(4.dp)),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
            Text(
                text = title,
                style = textStyle,
                modifier = Modifier
                    .weight(1f)
            )
            isExpanded?.let {
                Icon(
                    imageVector = expandIcon,
                    contentDescription = null,
                    modifier = modifier.clickable { onExpandClicked() }
                )
            }
        }
        if (isExpanded == true) {
            Divider(thickness = 1.dp)
        }
    }
}

@Preview
@Composable
fun TitleViewPreview() {
    NordicTheme {
        TitleView(
            icon = painterResource(id = R.drawable.alpha_n_box),
            title = stringResource(id = R.string.ndef_info),
            modifier = Modifier.padding(8.dp),
            textStyle = MaterialTheme.typography.headlineSmall
        ) {}
    }
}