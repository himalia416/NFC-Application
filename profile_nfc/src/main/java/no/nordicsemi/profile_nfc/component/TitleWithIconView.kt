package no.nordicsemi.profile_nfc.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import no.nordicsemi.profile_nfc.R
import no.nordicsemi.android.common.theme.NordicTheme

@Composable
fun TitleWithIcon(
    icon: Painter,
    title: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = modifier
                .size(50.dp)
                .clip(RoundedCornerShape(4.dp)),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
        )
        Text(
            text = title,
            style = textStyle,
            modifier = modifier
                .weight(1f)
        )
    }
}

@Preview
@Composable
fun LabelWithIconPreview() {
    NordicTheme {
        TitleWithIcon(
            icon = painterResource(id = R.drawable.alpha_n_box),
            title = stringResource(id = R.string.ndef_info),
            modifier = Modifier.padding(8.dp),
            textStyle = MaterialTheme.typography.headlineSmall
        )
    }
}