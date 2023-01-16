package io.lostimagin4tion.vkcup.ui.components.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.lostimagin4tion.vkcup.ui.screens.newDraggingIntoGaps.NewDraggingIntoGapsScreen
import io.lostimagin4tion.vkcup.ui.screens.newFillingGaps.NewFillingGapsScreen
import io.lostimagin4tion.vkcup.ui.screens.newMatching.NewMatchingScreen
import io.lostimagin4tion.vkcup.ui.screens.newPoll.NewPollScreen
import io.lostimagin4tion.vkcup.ui.screens.newRating.NewRatingScreen
import io.lostimagin4tion.vkcup.ui.screens.splash.SplashScreen
import io.lostimagin4tion.vkcup.ui.screens.watchPost.WatchPostScreen
import io.lostimagin4tion.vkcup.ui.screens.welcome.WelcomeScreen
import io.lostimagin4tion.vkcup.ui.screens.welcome.WelcomeScreenViewModel
import io.lostimagin4tion.vkcup.ui.screens.writePost.WritePostScreen
import io.lostimagin4tion.vkcup.ui.screens.writePost.WritePostViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * [Navigation] - composable function, which is intended to initialize navigation
 * between screens
 *
 * To add new screen, enter the corresponding path to [Routes]
 * and add new [composable] в [NavHost]
 *
 * @author Egor Danilov
 */
@Composable
fun Navigation(
    snackbarHostState: SnackbarHostState,
    paddingValues: PaddingValues,
    navController: NavHostController
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val showMessage: (Int) -> Unit = { message ->
        val strMessage = context.getString(message)
        scope.launch {
            snackbarHostState.showSnackbar(strMessage)
        }
    }

    NavigationContent(
        paddingValues = paddingValues,
        navController = navController,
        showMessage = showMessage,
    )
}

@Composable
fun NavigationContent(
    paddingValues: PaddingValues,
    navController: NavHostController,
    showMessage: (Int) -> Unit,
) {
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = remember { Routes.splash }
        ) {
            composable(Routes.splash) {
                SplashScreen()

                LaunchedEffect(Unit) {
                    delay(1000)
                    navController.navigate(Routes.welcome)
                }
            }

            composable(Routes.welcome) {
                val viewModel: WelcomeScreenViewModel = viewModel(
                    viewModelStoreOwner = viewModelStoreOwner
                )

                WelcomeScreen(
                    viewModel = viewModel,
                    navController = navController
                )
            }

            composable(Routes.writePost) {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    val welcomeScreenViewModel: WelcomeScreenViewModel = viewModel()
                    val writePostViewModel: WritePostViewModel = viewModel(
                        viewModelStoreOwner = viewModelStoreOwner
                    )

                    WritePostScreen(
                        welcomeScreenViewModel = welcomeScreenViewModel,
                        writePostViewModel = writePostViewModel,
                        navController = navController
                    )
                }

            }

            composable(Routes.newPollElement) {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    val postViewModel: WritePostViewModel = viewModel()

                    NewPollScreen(
                        postViewModel = postViewModel,
                        navController = navController,
                        showMessage = showMessage
                    )
                }
            }

            composable(Routes.newMatchingElement) {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    val postViewModel: WritePostViewModel = viewModel()

                    NewMatchingScreen(
                        postViewModel = postViewModel,
                        navController = navController,
                        showMessage = showMessage
                    )
                }
            }

            composable(Routes.newDraggingElement) {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    val postViewModel: WritePostViewModel = viewModel()

                    NewDraggingIntoGapsScreen(
                        postViewModel = postViewModel,
                        navController = navController,
                        showMessage = showMessage
                    )
                }
            }

            composable(Routes.newFillTheGapsElement) {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    val postViewModel: WritePostViewModel = viewModel()

                    NewFillingGapsScreen(
                        postViewModel = postViewModel,
                        navController = navController,
                        showMessage = showMessage
                    )
                }
            }

            composable(Routes.newRatingElement) {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    val postViewModel: WritePostViewModel = viewModel()

                    NewRatingScreen(
                        postViewModel = postViewModel,
                        navController = navController,
                        showMessage = showMessage
                    )
                }
            }

            composable(Routes.watchPost) {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    val welcomeScreenViewModel: WelcomeScreenViewModel = viewModel()

                    WatchPostScreen(
                        welcomeViewModel = welcomeScreenViewModel,
                        navController = navController
                    )
                }
            }
        }
    }
}
