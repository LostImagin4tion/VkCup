package io.lostimagin4tion.vkcup.ui.screens.newRating

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import io.lostimagin4tion.vkcup.R
import io.lostimagin4tion.vkcup.domain.enitites.InteractiveElementData
import io.lostimagin4tion.vkcup.domain.enitites.StarRating
import io.lostimagin4tion.vkcup.ui.components.appbars.AppBarWithMultipleButtons
import io.lostimagin4tion.vkcup.ui.components.buttons.StarButton
import io.lostimagin4tion.vkcup.ui.components.buttons.TextFilledButton
import io.lostimagin4tion.vkcup.ui.components.text.SubtitleText
import io.lostimagin4tion.vkcup.ui.screens.writePost.WritePostViewModel
import io.lostimagin4tion.vkcup.ui.theme.Dimensions
import io.lostimagin4tion.vkcup.ui.theme.VkCupTheme

/**
 * [NewMatchingScreen] - screen for interactive element that will look like clickable stars for
 * evaluating something
 *
 * @author Egor Danilov
 */
@Composable
fun NewRatingScreen(
    postViewModel: WritePostViewModel,
    navController: NavHostController,
    showMessage: (Int) -> Unit
) {
    val viewModel: NewRatingViewModel = viewModel()
    val ratingData by viewModel.ratingData.collectAsState()

    NewRatingScreenContent(
        ratingData = ratingData,
        getRememberedIndex = postViewModel::rememberedIndex::get,
        addStar = viewModel::addNewStar,
        deleteStar = viewModel::deleteStar,
        changeClickedStar = viewModel::changeClickedStar,
        finishCreatingRating = postViewModel::addInteractiveElement,
        navigateBack = navController::popBackStack,
        showMessage = showMessage
    )
}

/**
 * [NewRatingScreenContent] - layout of [NewRatingScreen]
 *
 * @author Egor Danilov
 */
@Composable
private fun NewRatingScreenContent(
    ratingData: StarRating,
    getRememberedIndex: () -> Int = { 0 },
    addStar: () -> Unit = {},
    deleteStar: () -> Unit = {},
    changeClickedStar: (Int) -> Unit = {},
    finishCreatingRating: (InteractiveElementData, Int) -> Unit = { _, _ -> },
    navigateBack: () -> Unit = {},
    showMessage: (Int) -> Unit = {}
) = Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.Start,
    modifier = Modifier
        .fillMaxSize()
        .imePadding()
) {
    var clickedStarIndex by remember { mutableStateOf(ratingData.clickedStarIndex) }

    val onContinueButtonClick: () -> Unit = {
        if (ratingData.size == 0) {
            showMessage(R.string.rating_creation_no_stars_error)
        }
        else {
            finishCreatingRating(ratingData, getRememberedIndex())
            navigateBack()
        }
    }

    AppBarWithMultipleButtons(
        title = {
            SubtitleText(
                textRes = R.string.new_rating_header,
                isLarge = false,
                modifier = Modifier.padding(start = Dimensions.commonPadding)
            )
        },
        navigationIconRes = R.drawable.ic_arrow_left,
        onNavigationIconClick = navigateBack,
        trailingIconRes = listOf(R.drawable.ic_check),
        onTrailingIconsClick = listOf(onContinueButtonClick)
    )

    Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding * 2))

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            verticalArrangement = Arrangement.spacedBy(Dimensions.mainHorizontalPadding),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.mainVerticalPadding),
            contentPadding = PaddingValues(horizontal = Dimensions.mainHorizontalPadding),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimensions.mainVerticalPadding)
        ) {
            items(count = ratingData.size) { index ->
                StarButton(
                    onClick = {
                        changeClickedStar(index)
                        clickedStarIndex = index
                    },
                    isFilled = index < clickedStarIndex + 1,
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = Dimensions.mainVerticalPadding * 4)
        ) {
            TextFilledButton(
                onClick = {
                    if (clickedStarIndex == ratingData.size - 1) {
                        clickedStarIndex = ratingData.size - 2
                    }
                    deleteStar()
                },
                textResource = R.string.remove_star_button,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                contentPadding = PaddingValues(
                    vertical = Dimensions.commonPadding,
                    horizontal = Dimensions.mainHorizontalPadding
                ),
                modifier = Modifier.padding(end = Dimensions.mainHorizontalPadding)
            )

            TextFilledButton(
                onClick = addStar,
                textResource = R.string.add_star_button,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                contentPadding = PaddingValues(
                    vertical = Dimensions.commonPadding,
                    horizontal = Dimensions.mainHorizontalPadding
                )
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFF9F9F9)
private fun NewRatingScreenPreview() = VkCupTheme {
    val starState by remember { mutableStateOf(StarRating()) }

    NewRatingScreenContent(starState)
}