package io.lostimagin4tion.vkcup.ui.components.buttons

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.lostimagin4tion.vkcup.ui.theme.Dimensions

/**
 * [TextFilledButton] - basic Material You filled button
 * Contains Text and optional Icon
 *
 * @author Egor Danilov
 */
@Composable
fun TextFilledButton(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(vertical = 10.dp),
    onClick: () -> Unit = {},
    colors: ButtonColors,
    @StringRes textResource: Int,
    @DrawableRes iconResource: Int? = null,
    isEnabled: Boolean = true
) = Button(
    onClick = onClick,
    enabled = isEnabled,
    contentPadding = contentPadding,
    colors = colors,
    modifier = modifier
) {
    iconResource?.let {
        Icon(
            painter = painterResource(iconResource),
            contentDescription = null,
            modifier = Modifier.size(30.dp)
        )
    }

    Text(
        text = stringResource(textResource),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.iconPadding(iconResource)
    )
}

fun Modifier.iconPadding(iconResource: Int?): Modifier =
    if (iconResource != null) then(this.padding(start = Dimensions.commonPadding)) else this