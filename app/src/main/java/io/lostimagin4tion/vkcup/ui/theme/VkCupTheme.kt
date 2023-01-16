package io.lostimagin4tion.vkcup.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = vkCupPrimary,
    onPrimary = vkCupPrimaryVariant,
    secondary = vkCupSecondary,
    onPrimaryContainer = vkCupPrimary,
    onSecondaryContainer = vkCupDarkGray,
    tertiary = correctAnswerColor,
    tertiaryContainer = vkCupDarkGray,
    background = vkCupDarkBackground,
    onBackground = Color.White,
    onSurface = vkCupDarkGray,
    inverseSurface = vkCupLightBackground,
    inverseOnSurface = Color.Black,
    error = vkCupError
)

private val LightColorScheme = lightColorScheme(
    primary = vkCupPrimaryVariant,
    onPrimary = vkCupPrimary,
    secondary = vkCupSecondary,
    onPrimaryContainer = vkCupPrimaryVariant,
    onSecondaryContainer = vkCupPrimary,
    tertiary = correctAnswerColor,
    tertiaryContainer = vkCupLightGray,
    background = vkCupLightBackground,
    onBackground = Color.Black,
    onSurface = Color.White,
    inverseSurface = vkCupDarkBackground,
    inverseOnSurface = Color.White,
    error = vkCupError
)

@Composable
fun VkCupTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme
        else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}