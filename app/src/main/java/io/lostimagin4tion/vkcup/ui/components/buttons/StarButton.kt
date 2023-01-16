package io.lostimagin4tion.vkcup.ui.components.buttons

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.with
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.lostimagin4tion.vkcup.R
import io.lostimagin4tion.vkcup.ui.theme.starButtonColor

/**
 * [StarButton] - main button for [NewRatingScreen]
 * supports content animation between filled and unfilled icons
 *
 * @author Egor Danilov
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun StarButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isFilled: Boolean = false,
) {
    val starIcon = @Composable {
        AnimatedContent(
            targetState = isFilled,
            transitionSpec = {
                scaleIn() with fadeOut()
            }
        ) { isFilled ->
            if (isFilled) {
                Icon(
                    painter = painterResource(R.drawable.ic_star_filled),
                    contentDescription = null,
                    modifier = modifier.size(44.dp),
                    tint = starButtonColor
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.ic_star),
                    contentDescription = null,
                    modifier = modifier.size(44.dp),
                    tint = starButtonColor
                )
            }
        }
    }

    IconButton(
        onClick = onClick,
        modifier = modifier.size(48.dp)
    ) {
        starIcon()
    }
}