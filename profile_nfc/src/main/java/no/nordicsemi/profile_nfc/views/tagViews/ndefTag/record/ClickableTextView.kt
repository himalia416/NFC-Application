package no.nordicsemi.profile_nfc.views.tagViews.ndefTag.record

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import no.nordicsemi.android.common.theme.NordicTheme

@Composable
fun ClickableTextView(tag: String, annotation: String) {
    val annotatedLinkString: AnnotatedString = buildAnnotatedString {
        append(annotation)
        addStringAnnotation(
            tag = tag,
            annotation = annotation,
            start = 0,
            end = annotation.length
        )

    }
    // UriHandler parse and opens URI inside AnnotatedString Item in Browser
    val uriHandler = LocalUriHandler.current

    ClickableText(text = annotatedLinkString) {
        annotatedLinkString
            .getStringAnnotations(tag, it, it)
            .firstOrNull()?.let { stringAnnotation ->
                uriHandler.openUri(stringAnnotation.item)
            }
    }
}

@Preview
@Composable
fun ClickableTextViewPreview() {
    NordicTheme {
        ClickableTextView("URL", "https://www.nordicsemi.com")
    }
}
