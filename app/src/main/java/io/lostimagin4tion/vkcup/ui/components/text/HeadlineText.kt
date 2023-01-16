package io.lostimagin4tion.vkcup.ui.components.text

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign

/**
 * [HeadlineText] is text for things like the text inside components
 * or for very small text in the content body, such as captions
 *
 * @param textRes resource on text, which will be displayed
 * @param isLarge true or false, depends on size of subtitle
 *
 * @author Egor Danilov
 */
@Composable
fun HeadlineText(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    isLarge: Boolean = false
) = BaseHeadlineText(
    text = stringResource(textRes),
    modifier = modifier,
    textAlign = textAlign,
    textColor = textColor,
    isLarge = isLarge
)

/**
 * [HeadlineText] is text for things like the text inside components
 * or for very small text in the content body, such as captions
 *
 * @param text -  string with text, which will be displayed
 * @param isLarge - true or false, depends on size of subtitle
 *
 * @author Egor Danilov
 */
@Composable
fun HeadlineText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    isLarge: Boolean = false
) = BaseHeadlineText(
    text = text,
    modifier = modifier,
    textAlign = textAlign,
    textColor = textColor,
    isLarge = isLarge
)

/**
 * [BaseHeadlineText] - base implementation of [HeadlineText]
 *
 * @param text - string with text, which will be displayed
 * @param isLarge true or false, depends on size of subtitle
 *
 * @author Egor Danilov
 */
@Composable
private fun BaseHeadlineText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    isLarge: Boolean = false
) = Text(
    text = text,
    style = if (isLarge) {
        MaterialTheme.typography.headlineMedium
    } else {
        MaterialTheme.typography.headlineSmall
    },
    modifier = modifier,
    textAlign = textAlign,
    color = textColor
)