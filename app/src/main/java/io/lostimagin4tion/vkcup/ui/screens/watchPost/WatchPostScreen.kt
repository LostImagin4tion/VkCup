package io.lostimagin4tion.vkcup.ui.screens.watchPost

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import io.lostimagin4tion.vkcup.R
import io.lostimagin4tion.vkcup.domain.enitites.ConvertedQuizWithGaps
import io.lostimagin4tion.vkcup.domain.enitites.GapsInteractionMethod
import io.lostimagin4tion.vkcup.domain.enitites.Matching
import io.lostimagin4tion.vkcup.domain.enitites.Poll
import io.lostimagin4tion.vkcup.domain.enitites.PostData
import io.lostimagin4tion.vkcup.domain.enitites.QuizWithGaps
import io.lostimagin4tion.vkcup.domain.enitites.StarRating
import io.lostimagin4tion.vkcup.domain.enitites.Text
import io.lostimagin4tion.vkcup.ui.components.appbars.AppBarWithMultipleButtons
import io.lostimagin4tion.vkcup.ui.components.interactiveElements.DraggingIntoGapsInteractiveElement
import io.lostimagin4tion.vkcup.ui.components.interactiveElements.FillingGapsInteractiveElement
import io.lostimagin4tion.vkcup.ui.components.interactiveElements.MatchingInteractiveElement
import io.lostimagin4tion.vkcup.ui.components.interactiveElements.PollInteractiveElement
import io.lostimagin4tion.vkcup.ui.components.interactiveElements.RatingInteractiveElement
import io.lostimagin4tion.vkcup.ui.components.text.LabelText
import io.lostimagin4tion.vkcup.ui.components.text.SubtitleText
import io.lostimagin4tion.vkcup.ui.screens.welcome.WelcomeScreenViewModel
import io.lostimagin4tion.vkcup.ui.theme.Dimensions

/**
 * [WatchPostScreen] - screen for watching specific post
 *
 * @author Egor Danilov
 */
@Composable
fun WatchPostScreen(
    welcomeViewModel: WelcomeScreenViewModel,
    navController: NavHostController
) {
    val watchPostViewModel: WatchPostViewModel = viewModel()

    val postData by welcomeViewModel.postsData.collectAsState()

    WatchPostScreenContent(
        postData = postData.posts[welcomeViewModel.clickedPostIndex],
        convertQuizWithGapsData = watchPostViewModel::convertQuizWithGapsData,
        navigateBack = navController::popBackStack
    )
}

/**
 * [WatchPostScreenContent] - layout for [WatchPostScreen] screen
 *
 * @author Egor Danilov
 */
@Composable
fun WatchPostScreenContent(
    postData: PostData,
    convertQuizWithGapsData:
        (QuizWithGaps) -> ConvertedQuizWithGaps = { ConvertedQuizWithGaps() },
    navigateBack: () -> Unit = {}
) = Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxSize()
        .imePadding()
) {
    AppBarWithMultipleButtons(
        navigationIconRes = R.drawable.ic_close,
        onNavigationIconClick = navigateBack,
        title = {
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
        }
    )

    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimensions.mainHorizontalPadding / 2)
    ) {
        item {
            Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding * 2))
        }

        items(postData.elements) { item ->
            when (item) {
                is Text -> {
                    LabelText(
                        text = item.text,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Dimensions.commonPadding)
                    )
                }
                is Poll -> {
                    PollInteractiveElement(data = item)
                }
                is Matching -> {
                    MatchingInteractiveElement(data = item)
                }
                is QuizWithGaps -> {
                    val conversionResult = convertQuizWithGapsData(item)

                    if (item.interactionMethod == GapsInteractionMethod.Dragging) {
                        DraggingIntoGapsInteractiveElement(data = conversionResult)
                    } else {
                        FillingGapsInteractiveElement(data = conversionResult)
                    }
                }
                is StarRating -> {
                    RatingInteractiveElement(data = item)
                }
                else -> {}
            }
            Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding * 2))
        }

        item {
            Spacer(modifier = Modifier.height(Dimensions.commonPadding))
        }
    }
}