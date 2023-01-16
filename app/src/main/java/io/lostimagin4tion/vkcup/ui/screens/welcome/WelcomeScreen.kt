package io.lostimagin4tion.vkcup.ui.screens.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.lostimagin4tion.vkcup.R
import io.lostimagin4tion.vkcup.domain.enitites.Posts
import io.lostimagin4tion.vkcup.ui.components.animations.AnimatedGif
import io.lostimagin4tion.vkcup.ui.components.appbars.SimpleLogoAppBar
import io.lostimagin4tion.vkcup.ui.components.buttons.TextFilledButton
import io.lostimagin4tion.vkcup.ui.components.cards.SmallCard
import io.lostimagin4tion.vkcup.ui.components.navigation.Routes
import io.lostimagin4tion.vkcup.ui.components.text.LabelText
import io.lostimagin4tion.vkcup.ui.components.text.SubtitleText
import io.lostimagin4tion.vkcup.ui.theme.Dimensions
import io.lostimagin4tion.vkcup.ui.theme.VkCupTheme

/**
 * [WelcomeScreen] - first screen of the app.
 * Displays list of posts, if it is not empty
 *
 * @author Egor Danilov
 */
@Composable
fun WelcomeScreen(
    viewModel: WelcomeScreenViewModel,
    navController: NavController
) {
    val postsData by viewModel.postsData.collectAsState()

    val onPostItemClick: (Int) -> Unit = {
        viewModel.clickedPostIndex = it
        navController.navigate(Routes.watchPost)
    }

    WelcomeScreenContent(
        postsData = postsData,
        navigateToPostWriting = { navController.navigate(Routes.writePost) },
        onPostItemClick = onPostItemClick,
        deletePost = viewModel::deletePost
    )
}

/**
 * [WelcomeScreenContent] - layout of [WelcomeScreen]
 *
 * @author Egor Danilov
 */
@Composable
private fun WelcomeScreenContent(
    postsData: Posts,
    navigateToPostWriting: () -> Unit = {},
    onPostItemClick: (Int) -> Unit = {},
    deletePost: (Int) -> Unit = {}
) = Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxSize()
        .imePadding()
) {

    SimpleLogoAppBar(
        trailingIconRes = R.drawable.ic_add,
        onTrailingIconClick = navigateToPostWriting
    )

    if (postsData.posts.isEmpty()) {
        WelcomeScreenEmptyContent(
            navigateToPostWriting = navigateToPostWriting
        )
    } else {
        WelcomeScreenPostsContent(
            postsData = postsData,
            onPostItemClick = onPostItemClick,
            deletePost = deletePost
        )
    }
}

@Composable
fun WelcomeScreenEmptyContent(
    navigateToPostWriting: () -> Unit = {}
) = Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.fillMaxSize()
) {
    AnimatedGif(
        gifRes = R.drawable.welcome_screen_animation,
        context = LocalContext.current,
        modifier = Modifier.size(300.dp)
    )

    SubtitleText(
        textRes = R.string.empty_screen_header,
        textAlign = TextAlign.Center,
        isLarge = true,
    )

    LabelText(
        textRes = R.string.empty_screen_desc,
        textAlign = TextAlign.Center,
        isLarge = false,
        modifier = Modifier.padding(top = Dimensions.commonPadding)
    )

    TextFilledButton(
        onClick = navigateToPostWriting,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        textResource = R.string.new_post_button,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dimensions.mainVerticalPadding * 2)
            .padding(horizontal = 36.dp)
    )
}

@Composable
fun WelcomeScreenPostsContent(
    postsData: Posts,
    onPostItemClick: (Int) -> Unit = {},
    deletePost: (Int) -> Unit = {},
) = Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = Dimensions.mainHorizontalPadding)
) {
    Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding * 2))

    LabelText(
        textRes = R.string.your_posts_header,
        isLarge = true,
        modifier = Modifier
            .padding(start = Dimensions.commonPadding)
            .align(Alignment.Start)
    )

    Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding))

    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        itemsIndexed(postsData.posts) { index, item ->
            SmallCard(
                onClick = { onPostItemClick(index) },
                onTrailingIconClick = { deletePost(index) },
                title = stringResource(R.string.post_item_header)
                    .format(
                        index + 1,
                        item.elements.size
                    ),
                iconRes = R.drawable.ic_planet,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding))
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFF9F9F9)
private fun WelcomeScreenPreview() = VkCupTheme {
    WelcomeScreenContent(Posts())
}