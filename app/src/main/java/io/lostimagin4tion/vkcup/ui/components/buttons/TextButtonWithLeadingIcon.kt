package io.lostimagin4tion.vkcup.ui.components.buttons

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.lostimagin4tion.vkcup.ui.theme.Dimensions

/**
 * [TextButtonWithLeadingIcon] - common Material text button with leading icon
 *
 * @author Egor Danilov
 */
@Composable
fun TextButtonWithLeadingIcon(
    @StringRes textRes: Int,
    @DrawableRes leadingIconRes: Int,
    onClick: () -> Unit = {}
) = TextButton(
    onClick = onClick,
    contentPadding = PaddingValues(
        start = Dimensions.commonPadding / 2,
        end = Dimensions.commonPadding
    ),
    colors = ButtonDefaults.textButtonColors(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.secondary
    )
) {
    Icon(
        painter = painterResource(leadingIconRes),
        contentDescription = null,
        modifier = Modifier.size(24.dp)
    )

    Text(
        text = stringResource(textRes),
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(start = Dimensions.commonPadding / 2),
    )
}