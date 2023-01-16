package io.lostimagin4tion.vkcup.ui.screens.writePost

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.lostimagin4tion.vkcup.R
import io.lostimagin4tion.vkcup.domain.enitites.GapsInteractionMethod
import io.lostimagin4tion.vkcup.domain.enitites.InteractiveElementItem
import io.lostimagin4tion.vkcup.domain.enitites.Matching
import io.lostimagin4tion.vkcup.domain.enitites.Poll
import io.lostimagin4tion.vkcup.domain.enitites.PostData
import io.lostimagin4tion.vkcup.domain.enitites.QuizWithGaps
import io.lostimagin4tion.vkcup.domain.enitites.StarRating
import io.lostimagin4tion.vkcup.domain.enitites.Text
import io.lostimagin4tion.vkcup.domain.enitites.interactiveElementItems
import io.lostimagin4tion.vkcup.ui.components.appbars.AppBarWithMultipleButtons
import io.lostimagin4tion.vkcup.ui.components.bottomsheet.CustomBottomSheetScaffold
import io.lostimagin4tion.vkcup.ui.components.bottomsheet.CustomBottomSheetValue
import io.lostimagin4tion.vkcup.ui.components.bottomsheet.rememberCustomBottomSheetScaffoldState
import io.lostimagin4tion.vkcup.ui.components.bottomsheet.rememberCustomBottomSheetState
import io.lostimagin4tion.vkcup.ui.components.buttons.TextButtonWithLeadingIcon
import io.lostimagin4tion.vkcup.ui.components.cards.MediumCard
import io.lostimagin4tion.vkcup.ui.components.cards.SmallCard
import io.lostimagin4tion.vkcup.ui.components.contentBlocks.ClosingBlock
import io.lostimagin4tion.vkcup.ui.components.contentBlocks.DefaultBlock
import io.lostimagin4tion.vkcup.ui.components.text.SubtitleText
import io.lostimagin4tion.vkcup.ui.components.textFields.SimpleTextField
import io.lostimagin4tion.vkcup.ui.screens.welcome.WelcomeScreenViewModel
import io.lostimagin4tion.vkcup.ui.theme.Dimensions
import io.lostimagin4tion.vkcup.ui.theme.VkCupTheme
import kotlinx.coroutines.launch

/**
 * [WritePostScreen] - screen for writing posts. From there you can navigate to adding all
 * interactive elements.
 *
 * @author Egor Danilov
 */
@Composable
fun WritePostScreen(
    welcomeScreenViewModel: WelcomeScreenViewModel,
    writePostViewModel: WritePostViewModel,
    navController: NavController
) {
    val postData by writePostViewModel.writePostData.collectAsState()

    val addTextElement: () -> Unit = {
        writePostViewModel.addInteractiveElement(
            element = Text(),
            index = writePostViewModel.rememberedIndex
        )
    }

    WritePostScreenContent(
        postData = postData,
        updateTextElement = writePostViewModel::updateTextElement,
        deleteElement = writePostViewModel::deleteInteractiveElement,
        addTextElement = addTextElement,
        setRememberedIndex = writePostViewModel::rememberedIndex::set,
        navigateToInteractiveElement = navController::navigate,
        finishCreatingPost = welcomeScreenViewModel::addPost,
        navigateBack = navController::popBackStack,
    )
}

/**
 * [WritePostScreenContent] - layout for [WritePostScreen]
 *
 * @author Egor Danilov
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun WritePostScreenContent(
    postData: PostData,
    updateTextElement: (String, Int) -> Unit = { _, _ -> },
    deleteElement: (Int) -> Unit = {},
    addTextElement: () -> Unit = {},
    setRememberedIndex: (Int) -> Unit = {},
    navigateToInteractiveElement: (String) -> Unit = {},
    finishCreatingPost: (PostData) -> Unit = {},
    navigateBack: () -> Unit = {},
) {
    val sheetState = rememberCustomBottomSheetState(
        initialValue = CustomBottomSheetValue.Collapsed
    )
    val scaffoldState = rememberCustomBottomSheetScaffoldState(
        customBottomSheetState = sheetState
    )
    val coroutineScope = rememberCoroutineScope()

    val showBottomSheet: () -> Unit = {
        coroutineScope.launch {
            scaffoldState.customBottomSheetState.upToHalf()
        }
    }
    val hideBottomSheet: () -> Unit = {
        coroutineScope.launch {
            scaffoldState.customBottomSheetState.collapse()
        }
    }

    CustomBottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Spacer(modifier = Modifier.height(20.dp))

            WritePostScreenBottomSheetHeader()

            Spacer(modifier = Modifier.height(6.dp))

            WritePostScreenBottomSheetContent(
                interactiveElementItems = interactiveElementItems,
                navigateToInteractiveElement = navigateToInteractiveElement,
                addTextElement = addTextElement,
                hideBottomSheet = hideBottomSheet
            )
        },
        sheetPeekHeight = 0.dp,
        sheetElevation = 8.dp,
        backgroundColor = MaterialTheme.colorScheme.background,
        sheetBackgroundColor = MaterialTheme.colorScheme.onSecondaryContainer,
        halfCoefficient = 0.65f,
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        WritePostScreenMainContent(
            postData = postData,
            updateTextElement = updateTextElement,
            deleteElement = deleteElement,
            setRememberedIndex = setRememberedIndex,
            showBottomSheet = showBottomSheet,
            finishCreatingPost = finishCreatingPost,
            navigateBack = navigateBack,
        )
    }
}

@Composable
private fun WritePostScreenMainContent(
    postData: PostData,
    updateTextElement: (String, Int) -> Unit = { _, _ -> },
    deleteElement: (Int) -> Unit = {},
    setRememberedIndex: (Int) -> Unit = {},
    showBottomSheet: () -> Unit = {},
    finishCreatingPost: (PostData) -> Unit = {},
    navigateBack: () -> Unit = {},
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
        trailingIconRes = listOf(R.drawable.ic_arrow_right),
        onTrailingIconsClick = listOf {
            finishCreatingPost(postData)
            navigateBack()
        }
    )

    Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding))

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(Dimensions.commonPadding),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.mainHorizontalPadding)
    ) {
        if (postData.elements.isEmpty()) {
            item {
                TextButtonWithLeadingIcon(
                    textRes = R.string.add_new_content_block_button,
                    leadingIconRes = R.drawable.ic_add_with_circle,
                    onClick = showBottomSheet
                )
            }
        }
        else {
            itemsIndexed(postData.elements) { index, item ->
                var content: @Composable (() -> Unit)? = null

                when (item) {
                    is Text -> {
                        var inputState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                            mutableStateOf(TextFieldValue(item.text))
                        }

                        content = @Composable {
                            SimpleTextField(
                                value = inputState,
                                onValueChange = {
                                    inputState = it
                                    updateTextElement(it.text, index)
                                },
                                singleLine = false,
                                placeholderRes = R.string.write_post_text_field_default
                            )
                        }
                    }
                    is Poll -> {
                        val header = stringResource(R.string.poll_header)
                            .format(item.theme)

                        content = @Composable {
                            SmallCard(
                                title = header,
                                iconRes = R.drawable.ic_poll,
                                onTrailingIconClick = { deleteElement(index) }
                            )
                        }
                    }
                    is Matching -> {
                        val header = stringResource(R.string.matching_header)
                            .format(item.items.size)

                        content = @Composable {
                            SmallCard(
                                title = header,
                                iconRes = R.drawable.ic_matching,
                                onTrailingIconClick = { deleteElement(index) }
                            )
                        }
                    }
                    is QuizWithGaps -> {
                        if (item.interactionMethod == GapsInteractionMethod.Dragging) {
                            val header = stringResource(R.string.dragging_into_gaps_header)
                                .format(item.gaps.size)

                            content = @Composable {
                                SmallCard(
                                    title = header,
                                    iconRes = R.drawable.ic_dragging,
                                    onTrailingIconClick = { deleteElement(index) }
                                )
                            }
                        } else {
                            val header = stringResource(R.string.filling_gaps_header)
                                .format(item.gaps.size)

                            content = @Composable {
                                SmallCard(
                                    title = header,
                                    iconRes = R.drawable.ic_fill_the_gap,
                                    onTrailingIconClick = { deleteElement(index) }
                                )
                            }
                        }
                    }
                    is StarRating -> {
                        val header = stringResource(R.string.rating_header)
                            .format(item.size)

                        content = @Composable {
                            SmallCard(
                                title = header,
                                iconRes = R.drawable.ic_star_filled,
                                onTrailingIconClick = { deleteElement(index) }
                            )
                        }
                    }
                    else -> {}
                }

                content?.let {
                    if (index == 0) {
                        DefaultBlock(
                            onFirstAddBlockClick = {
                                setRememberedIndex(0)
                                showBottomSheet()
                            },
                            onSecondAddBlockClick = {
                                setRememberedIndex(1)
                                showBottomSheet()
                            },
                            middleContent = it
                        )
                    } else {
                        ClosingBlock(
                            onAddBlockClick = {
                                setRememberedIndex(index + 1)
                                showBottomSheet()
                            },
                            endContent = it
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding))
        }
    }
}

@Composable
private fun ColumnScope.WritePostScreenBottomSheetHeader() {
    val width = (LocalConfiguration.current.screenWidthDp * 0.125f).dp

    Divider(
        thickness = 3.dp,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .width(width)
            .align(Alignment.CenterHorizontally)
    )

    Spacer(modifier = Modifier.height(14.dp))

    SubtitleText(
        textRes = R.string.bottom_sheet_header,
        isLarge = false,
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(bottom = 6.dp),
    )
}

@Composable
private fun ColumnScope.WritePostScreenBottomSheetContent(
    interactiveElementItems: List<InteractiveElementItem>,
    navigateToInteractiveElement: (String) -> Unit = {},
    addTextElement: () -> Unit = { },
    hideBottomSheet: () -> Unit = {}
) {
    BackHandler { hideBottomSheet() }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(Dimensions.mainHorizontalPadding / 2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(interactiveElementItems) { item ->
            MediumCard(
                onClick = if (item.route.isNotBlank()) {
                    {
                        navigateToInteractiveElement(item.route)
                        hideBottomSheet()
                    }
                } else {
                    {
                        addTextElement()
                        hideBottomSheet()
                    }
                },
                titleRes = item.nameRes,
                iconRes = item.iconRes,
                backgroundColor = item.backgroundColor
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFF9F9F9)
private fun WritePostScreenPreview() = VkCupTheme {
    val postData by remember { mutableStateOf(PostData()) }

    WritePostScreenContent(postData)
}