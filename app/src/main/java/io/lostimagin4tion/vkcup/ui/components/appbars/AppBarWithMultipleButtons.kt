package io.lostimagin4tion.vkcup.ui.components.appbars

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.lostimagin4tion.vkcup.ui.theme.Dimensions

/**
 * [AppBarWithMultipleButtons] - top app bar that supports displaying title text
 * and multiple buttons
 *
 * @param navigationIconRes - resource of trailing icon that will be displayed
 * on the left of the screen
 *
 * @param trailingIconRes - list of trailing icon buttons that will be displayed
 * on the right of the screen.
 *
 * @param onTrailingIconsClick - list of actions for trailing icon buttons
 *
 * @author Egor Danilov
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarWithMultipleButtons(
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    title: @Composable () -> Unit = {},
    @DrawableRes navigationIconRes: Int,
    onNavigationIconClick: () -> Unit = {},
    @DrawableRes trailingIconRes: List<Int> = emptyList(),
    onTrailingIconsClick: List<() -> Unit> = List(trailingIconRes.size) { {} }
) = TopAppBar(
    title = title,
    colors = TopAppBarDefaults.smallTopAppBarColors(
        containerColor = backgroundColor
    ),
    windowInsets = WindowInsets(top = 0.dp),
    navigationIcon = {
        IconButton(
            onClick = onNavigationIconClick,
            modifier = Modifier
                .padding(start = Dimensions.topAppBarHorizontalPadding)
                .size(30.dp)
        ) {
            Icon(
                painter = painterResource(navigationIconRes),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        }
    },
    actions = {
        trailingIconRes.zip(onTrailingIconsClick) { icon, action ->
            IconButton(
                onClick = action,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .size(30.dp)
            ) {
                Icon(
                    painter = painterResource(icon),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
)