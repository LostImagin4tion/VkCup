package io.lostimagin4tion.vkcup.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import io.lostimagin4tion.vkcup.R
import io.lostimagin4tion.vkcup.ui.theme.VkCupTheme
import io.lostimagin4tion.vkcup.ui.theme.vkCupDarkBackground
import kotlinx.coroutines.delay

/**
 * [SplashScreen] - screen with logo and loader,
 * that is shown when the application is launched
 *
 * @author Egor Danilov
 */
@Composable
fun SplashScreen() {
    SplashScreenContent()
}

@Composable
fun SplashScreenContent() = ConstraintLayout(
    modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
) {
    val (logo, loader) = createRefs()

    Image(
        painter = painterResource(R.drawable.logo_zen),
        contentDescription = null,
        modifier = Modifier
            .constrainAs(logo) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            }
            .padding(horizontal = 30.dp)
            .padding(top = 200.dp)
            .size(width = 200.dp, height = 90.dp)
    )

    CircularProgressIndicator(
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
            .constrainAs(loader) {
                centerHorizontallyTo(parent)
                bottom.linkTo(parent.bottom)
            }
            .padding(bottom = 100.dp)
            .size(48.dp)
    )
}

@Composable
@Preview(showBackground = true)
fun AuthorizationScreenPreview() = VkCupTheme {
    SplashScreenContent()
}