package no.nordicsemi.profile_nfc.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.rememberBalloonBuilder
import com.skydoves.balloon.compose.setBackgroundColor
import com.skydoves.balloon.compose.setTextColor
import no.nordicsemi.android.common.theme.NordicTheme
import no.nordicsemi.profile_nfc.R

@Composable
fun TooltipPopUp(tooltipText: String) {
    val textColor = MaterialTheme.colorScheme.onSurface
    val bgColor = MaterialTheme.colorScheme.surface

    val builder = rememberBalloonBuilder {
        setArrowSize(8)
        setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
        setArrowPosition(0.5f)
        setWidth(BalloonSizeSpec.WRAP)
        setHeight(BalloonSizeSpec.WRAP)
        setPadding(16)
        setCornerRadius(8f)
        setTextColor(textColor)
        setBackgroundColor(bgColor)
        setDismissWhenClicked(true)
    }
    Box {
        Balloon(
            builder = builder,
            balloonContent = { Text(text = tooltipText) }
        ) { balloonWindow ->
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                modifier = Modifier.clickable { balloonWindow.showAlignTop() }
            )
        }
    }
}

@Preview
@Composable
fun TooltipPopUpPreview() {
    NordicTheme {
        TooltipPopUp(stringResource(id = R.string.atqa_des))
    }
}
