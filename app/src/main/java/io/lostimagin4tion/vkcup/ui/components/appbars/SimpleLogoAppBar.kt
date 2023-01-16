package io.lostimagin4tion.vkcup.ui.components.appbars

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import io.lostimagin4tion.vkcup.R
import io.lostimagin4tion.vkcup.ui.components.icons.IconWithBackground
import io.lostimagin4tion.vkcup.ui.components.text.LabelText
import io.lostimagin4tion.vkcup.ui.components.text.SubtitleText
import io.lostimagin4tion.vkcup.ui.theme.Dimensions

/**
 * [SimpleLogoAppBar] - simple app bar that displays Zen logo on the left of the screen and
 * trailing icon on the right
 *
 * @author Egor Danilov
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleLogoAppBar(
    @DrawableRes trailingIconRes: Int? = null,
    onTrailingIconClick: () -> Unit = {}
) = TopAppBar(
    title = {},
    colors = TopAppBarDefaults.smallTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.background
    ),
    windowInsets = WindowInsets(top = 0.dp),
    navigationIcon = {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = Dimensions.topAppBarHorizontalPadding)
        ) {
            Image(
                painter = painterResource(R.drawable.logo_zen),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )

            SubtitleText(
                textRes = R.string.zen_label,
                isLarge = true,
                modifier = Modifier.padding(start = Dimensions.commonPadding)
            )
        }
    },
    actions = {
        trailingIconRes?.let {
            IconButton(
                onClick = onTrailingIconClick,
                modifier = Modifier
                    .padding(end = Dimensions.topAppBarHorizontalPadding)
                    .size(30.dp)
            ) {
                IconWithBackground(
                    iconRes = trailingIconRes,
                    iconColor = MaterialTheme.colorScheme.onPrimary,
                    backgroundColor = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(10.dp),
                    size = 30.dp
                )
            }
        }
    }
)