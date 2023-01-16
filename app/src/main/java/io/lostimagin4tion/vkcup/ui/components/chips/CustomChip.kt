package io.lostimagin4tion.vkcup.ui.components.chips

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.lostimagin4tion.vkcup.ui.theme.Dimensions
import io.lostimagin4tion.vkcup.ui.theme.vkCupChipDivider

/**
 * [CustomChip] - toggleable chip, supports change of color after click and reveal animation
 * As Compose chips don't support custom content paddings, I had to implement my own
 *
 * @author Egor Danilov
 */
@Composable
fun CustomChip(
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
    onTrailingIconClick: () -> Unit = {},
    label: String,
    chipColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    selectedColor: Color = chipColor,
    @DrawableRes trailingIconId: Int? = null,
    @DrawableRes selectedTrailingIconId: Int? = trailingIconId
) {
    val animateShape = remember { Animatable(15f) }
    LaunchedEffect(animateShape) {
        animateShape.animateTo(
            targetValue = 0f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessMediumLow
            )
        )
    }

    Surface(
        shape = MaterialTheme.shapes.medium,
        color = if (isSelected) selectedColor else chipColor,
        modifier = Modifier
            .border(
                width = Dp(animateShape.value),
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.medium
            )
            .toggleable(
                value = isSelected,
                onValueChange = { onClick() }
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    vertical = Dimensions.chipVerticalPadding,
                    horizontal = Dimensions.chipHorizontalPadding
                )
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = contentColor,
            )

            if (trailingIconId != null && selectedTrailingIconId != null) {
                Spacer(modifier = Modifier.width(14.dp))

                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = vkCupChipDivider,
                    modifier = Modifier.size(width = 1.dp, height = 20.dp),
                    content = {}
                )

                IconButton(
                    onClick = onTrailingIconClick,
                    modifier = Modifier
                        .size(26.dp)
                        .padding(start = 8.dp)
                ) {
                    Icon(
                        painter = if (isSelected) painterResource(selectedTrailingIconId)
                        else painterResource(trailingIconId),
                        contentDescription = null,
                        tint = contentColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}