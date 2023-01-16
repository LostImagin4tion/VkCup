package io.lostimagin4tion.vkcup.ui.components.cards

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.lostimagin4tion.vkcup.R
import io.lostimagin4tion.vkcup.ui.components.text.LabelText
import io.lostimagin4tion.vkcup.ui.theme.Dimensions
import io.lostimagin4tion.vkcup.ui.theme.VkCupTheme
import io.lostimagin4tion.vkcup.ui.theme.vkCupDarkGray

/**
 * [SmallCard] - small card for displaying basic interactive element info
 * (e.g in [WritePostScreen])
 *
 * @author Egor Danilov
 */
@Composable
fun SmallCard(
    title: String,
    @DrawableRes iconRes: Int,
    modifier: Modifier = Modifier,
    backgroundColor: Color = vkCupDarkGray,
    onClick: () -> Unit = {},
    onTrailingIconClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(30))
            .background(backgroundColor)
            .clickable { onClick() }
    ) {
        Spacer(modifier = Modifier
            .width(Dimensions.mainHorizontalPadding / 2)
            .height(36.dp + Dimensions.commonPadding * 2)
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .size(36.dp)
                .background(backgroundColor.copy(0.5f))
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.25F),
                    shape = CircleShape
                ),
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
            )
        }

        LabelText(
            text = title,
            textColor = Color.White,
            modifier = Modifier
                .padding(start = Dimensions.commonPadding)
                .padding(vertical = Dimensions.commonPadding)
                .weight(1f)
        )

        if (onTrailingIconClick != {}) {
            Spacer(modifier = Modifier.width(Dimensions.mainHorizontalPadding / 2))

            IconButton(
                onClick = onTrailingIconClick,
                modifier = Modifier
                    .size(28.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(Dimensions.mainHorizontalPadding / 2))
    }
}

@Composable
@Preview
fun InteractiveElementSmallCardPreview() = VkCupTheme{
    SmallCard(
        title = "Poll (5 questions)",
        iconRes = R.drawable.ic_poll,
        backgroundColor = vkCupDarkGray
    )
}