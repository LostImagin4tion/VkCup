package io.lostimagin4tion.vkcup.ui.components.icons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp

/**
 * [IconWithBackground] - simple Material icon with background
 *
 * @author Egor Danilov
 */
@Composable
fun IconWithBackground(
    @DrawableRes iconRes: Int,
    iconColor: Color,
    backgroundColor: Color,
    size: Dp,
    shape: RoundedCornerShape
) = Icon(
    painter = painterResource(iconRes),
    contentDescription = null,
    tint = iconColor,
    modifier = Modifier
        .background(
            color = backgroundColor,
            shape = shape
        )
        .size(size)
)