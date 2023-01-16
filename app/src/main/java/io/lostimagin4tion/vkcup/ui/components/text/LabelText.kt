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
 * [LabelText] is text for things like the text inside components
 * or for very small text in the content body, such as captions
 *
 * @param textRes resource on text, which will be displayed
 * @param isLarge true or false, depends on size of subtitle
 *
 * @author Egor Danilov
 */
@Composable
fun LabelText(
    @StringRes textRes: Int,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    isLarge: Boolean = false
) = BaseLabelText(
    text = stringResource(textRes),
    modifier = modifier,
    textAlign = textAlign,
    textColor = textColor,
    isLarge = isLarge
)

/**
 * [LabelText] is text for things like the text inside components
 * or for very small text in the content body, such as captions
 *
 * @param text -  string with text, which will be displayed
 * @param isLarge - true or false, depends on size of subtitle
 *
 * @author Egor Danilov
 */
@Composable
fun LabelText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    isLarge: Boolean = false
) = BaseLabelText(
    text = text,
    modifier = modifier,
    textAlign = textAlign,
    textColor = textColor,
    isLarge = isLarge
)

/**
 * [BaseLabelText] - base implementation of [LabelText]
 *
 * @param text - string with text, which will be displayed
 * @param isLarge true or false, depends on size of subtitle
 *
 * @author Egor Danilov
 */
@Composable
private fun BaseLabelText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    isLarge: Boolean = false
) = Text(
    text = text,
    style = if (isLarge) {
        MaterialTheme.typography.labelLarge
    } else {
        MaterialTheme.typography.labelMedium
    },
    modifier = modifier,
    textAlign = textAlign,
    color = textColor
)