package io.lostimagin4tion.vkcup.ui.screens.newMatching

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import io.lostimagin4tion.vkcup.R
import io.lostimagin4tion.vkcup.domain.enitites.InteractiveElementData
import io.lostimagin4tion.vkcup.domain.enitites.Matching
import io.lostimagin4tion.vkcup.ui.components.appbars.AppBarWithMultipleButtons
import io.lostimagin4tion.vkcup.ui.components.buttons.TextButtonWithLeadingIcon
import io.lostimagin4tion.vkcup.ui.components.text.LabelText
import io.lostimagin4tion.vkcup.ui.components.text.SubtitleText
import io.lostimagin4tion.vkcup.ui.screens.writePost.WritePostViewModel
import io.lostimagin4tion.vkcup.ui.theme.Dimensions
import io.lostimagin4tion.vkcup.ui.theme.VkCupTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import io.lostimagin4tion.vkcup.ui.components.textFields.CustomOutlinedTextField

/**
 * [NewMatchingScreen] - screen for interactive element that will work like
 * mapping elements between two columns
 *
 * @author Egor Danilov
 */
@Composable
fun NewMatchingScreen(
    postViewModel: WritePostViewModel,
    navController: NavHostController,
    showMessage: (Int) -> Unit
) {
    val viewModel: NewMatchingViewModel = viewModel()

    val matchingData by viewModel.matchingData.collectAsState()

    NewMatchingScreenContent(
        questionAnswerPairs = matchingData,
        getRememberedIndex = postViewModel::rememberedIndex::get,
        addQuestionAnswerPair = viewModel::addQuestionAnswerPair,
        deleteQuestionAnswerPair = viewModel::deleteQuestionAnswerPair,
        changeQuestionText = viewModel::changeQuestionText,
        changeAnswerText = viewModel::changeAnswerText,
        finishCreatingMatching = postViewModel::addInteractiveElement,
        navigateBack = navController::popBackStack,
        showMessage = showMessage,
    )
}

/**
 * [NewMatchingScreenContent] - layout of [NewMatchingScreen]
 *
 * @author Egor Danilov
 */
@Composable
private fun NewMatchingScreenContent(
    questionAnswerPairs: Matching,
    getRememberedIndex: () -> Int = { 0 },
    addQuestionAnswerPair: () -> Unit = {},
    deleteQuestionAnswerPair: (Int) -> Unit = {},
    changeQuestionText: (String, Int) -> Unit = { _, _ -> },
    changeAnswerText: (String, Int) -> Unit = { _, _ -> },
    finishCreatingMatching: (InteractiveElementData, Int) -> Unit = { _, _ ->},
    showMessage: (Int) -> Unit = {},
    navigateBack: () -> Unit = {}
) = Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.Start,
    modifier = Modifier
        .fillMaxSize()
        .imePadding()
) {
    var isContinueButtonClicked by rememberSaveable { mutableStateOf(false) }
    val questionAnswerErrorsList = mutableListOf<State<Boolean>>()

    val onContinueButtonCLick: () -> Unit = {
        isContinueButtonClicked = true

        if (questionAnswerPairs.items.isEmpty()) {
            showMessage(R.string.matching_creation_no_pairs_error)
        }
        else if (questionAnswerErrorsList.all { !it.value }) {
            finishCreatingMatching(questionAnswerPairs, getRememberedIndex())
            navigateBack()
        }
        else {
            showMessage(R.string.matching_creation_empty_fields_error)
        }
    }

    AppBarWithMultipleButtons(
        title = {
            SubtitleText(
                textRes = R.string.new_matching_header,
                isLarge = false,
                modifier = Modifier.padding(start = Dimensions.commonPadding)
            )
        },
        navigationIconRes = R.drawable.ic_arrow_left,
        onNavigationIconClick = navigateBack,
        trailingIconRes = listOf(
            R.drawable.ic_question,
            R.drawable.ic_check
        ),
        onTrailingIconsClick = listOf(
            { showMessage(R.string.matching_hint) },
            onContinueButtonCLick
        )
    )

    Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding))

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(horizontal = Dimensions.mainHorizontalPadding / 2)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(Dimensions.mainHorizontalPadding),
            horizontalAlignment = Alignment.Start,
            contentPadding = PaddingValues(horizontal = Dimensions.mainHorizontalPadding),
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(questionAnswerPairs.items) { index, item ->
                var questionInputState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                    mutableStateOf(TextFieldValue(item.question))
                }
                var answerInputState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                    mutableStateOf(TextFieldValue(item.answer))
                }
                val questionErrorState = rememberSaveable {
                    mutableStateOf(questionInputState.text.isBlank())
                }
                val answerErrorState = rememberSaveable {
                    mutableStateOf(questionInputState.text.isBlank())
                }
                questionAnswerErrorsList.add(questionErrorState)
                questionAnswerErrorsList.add(answerErrorState)

                LabelText(
                    text = stringResource(R.string.poll_question_number)
                        .format(
                            index + 1,
                            questionAnswerPairs.items.size
                        ),
                    isLarge = false,
                    modifier = Modifier.padding(start = Dimensions.commonPadding)
                )

                Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding / 2))

                CustomOutlinedTextField(
                    value = questionInputState,
                    onValueChange = {
                        questionErrorState.value = it.text.isBlank()
                        questionInputState = it
                        changeQuestionText(it.text, index)
                    },
                    onDeleteIconClick = { deleteQuestionAnswerPair(index) },
                    trailingIconEnabled = false,
                    isError = questionErrorState.value && isContinueButtonClicked
                )

                Spacer(modifier = Modifier.height(Dimensions.commonPadding))

                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(start = Dimensions.mainHorizontalPadding * 2)
                ) {
                    LabelText(
                        text = stringResource(R.string.answer_header)
                            .format(
                                index + 1,
                                questionAnswerPairs.items.size
                            ),
                        isLarge = false,
                        modifier = Modifier.padding(start = Dimensions.commonPadding)
                    )

                    Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding / 2))

                    CustomOutlinedTextField(
                        value = answerInputState,
                        onValueChange = {
                            answerErrorState.value = it.text.isBlank()
                            answerInputState = it
                            changeAnswerText(it.text, index)
                        },
                        deleteIconEnabled = false,
                        trailingIconEnabled = false,
                        isError = answerErrorState.value && isContinueButtonClicked,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding / 2))
            }

            item {
                TextButtonWithLeadingIcon(
                    onClick = addQuestionAnswerPair,
                    textRes = R.string.add_matching_button,
                    leadingIconRes = R.drawable.ic_add_with_circle
                )

                Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding * 2))
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFF9F9F9)
private fun NewMatchingScreenPreview() = VkCupTheme {
    val matching by remember { mutableStateOf(Matching()) }

    NewMatchingScreenContent(matching)
}