package io.lostimagin4tion.vkcup.ui.screens.newPoll

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import io.lostimagin4tion.vkcup.R
import io.lostimagin4tion.vkcup.domain.enitites.AnswerOption
import io.lostimagin4tion.vkcup.domain.enitites.InteractiveElementData
import io.lostimagin4tion.vkcup.domain.enitites.Poll
import io.lostimagin4tion.vkcup.domain.enitites.PollQuestion
import io.lostimagin4tion.vkcup.ui.components.appbars.AppBarWithMultipleButtons
import io.lostimagin4tion.vkcup.ui.components.buttons.TextButtonWithLeadingIcon
import io.lostimagin4tion.vkcup.ui.components.text.LabelText
import io.lostimagin4tion.vkcup.ui.components.text.SubtitleText
import io.lostimagin4tion.vkcup.ui.components.textFields.CustomOutlinedTextField
import io.lostimagin4tion.vkcup.ui.screens.writePost.WritePostViewModel
import io.lostimagin4tion.vkcup.ui.theme.Dimensions
import io.lostimagin4tion.vkcup.ui.theme.VkCupTheme

/**
 * [NewPollScreen] - screen for interactive element that will look like multistage poll
 *
 * @author Egor Danilov
 */
@Composable
fun NewPollScreen(
    postViewModel: WritePostViewModel,
    navController: NavHostController,
    showMessage: (Int) -> Unit
) {
    val pollViewModel: NewPollViewModel = viewModel()

    val pollData by pollViewModel.pollData.collectAsState()

    NewPollScreenContent(
        pollData = pollData,
        getRememberedIndex = postViewModel::rememberedIndex::get,
        addNewQuestion = pollViewModel::addNewQuestion,
        addNewAnswerOption = pollViewModel::addNewAnswerOption,
        changePollTheme = pollViewModel::changePollTheme,
        changeQuestionText = pollViewModel::changeQuestionText,
        changeAnswerOptionText = pollViewModel::changeAnswerOptionText,
        deleteQuestion = pollViewModel::deleteQuestion,
        deleteAnswer = pollViewModel::deleteAnswer,
        updateCorrectAnswer = pollViewModel::updateCorrectAnswer,
        finishCreatingPoll = postViewModel::addInteractiveElement,
        navigateBack = navController::popBackStack,
        showMessage = showMessage
    )
}

/**
 * [NewPollScreenContent] - layout of [NewPollScreen]
 *
 * @author Egor Danilov
 */
@Composable
private fun NewPollScreenContent(
    pollData: Poll,
    getRememberedIndex: () -> Int = { 0 },
    addNewQuestion: (String) -> Unit = {},
    addNewAnswerOption: (Int, String, Boolean) -> Unit = { _, _, _ -> },
    changePollTheme: (String) -> Unit = {},
    changeQuestionText: (String, Int) -> Unit = { _, _ -> },
    changeAnswerOptionText: (String, Int, Int) -> Unit = { _, _, _ -> },
    deleteQuestion: (Int) -> Unit = {},
    deleteAnswer: (Int, Int) -> Unit = { _, _ -> },
    updateCorrectAnswer: (Int, Int, Boolean) -> Unit = { _, _, _ -> },
    finishCreatingPoll: (InteractiveElementData, Int) -> Unit = { _, _ -> },
    navigateBack: () -> Unit = {},
    showMessage: (Int) -> Unit = {}
) = Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.Start,
    modifier = Modifier
        .fillMaxSize()
        .imePadding()
) {
    var themeInputState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(pollData.theme))
    }
    var isContinueButtonClicked by rememberSaveable { mutableStateOf(false) }

    var isThemeInputError by rememberSaveable { mutableStateOf(themeInputState.text.isBlank()) }
    val questionAnswerErrorsList = mutableListOf<State<Boolean>>()

    val onContinueButtonClick: () -> Unit = {
        isContinueButtonClicked = true

        if (pollData.questions.isEmpty()) {
            showMessage(R.string.poll_creation_no_questions_error)
        }
        else if (!pollData.questions.all { it.answerOptions.isNotEmpty() }) {
            showMessage(R.string.poll_creation_no_answers_error)
        }
        else if (!isThemeInputError && questionAnswerErrorsList.all { !it.value }) {
            finishCreatingPoll(pollData, getRememberedIndex())
            navigateBack()
        }
        else {
            showMessage(R.string.poll_creation_empty_fields_error)
        }
    }

    AppBarWithMultipleButtons(
        title = {
            SubtitleText(
                textRes = R.string.new_poll_header,
                isLarge = false,
                modifier = Modifier.padding(start = Dimensions.commonPadding)
            )
        },
        navigationIconRes = R.drawable.ic_arrow_left,
        onNavigationIconClick = navigateBack,
        trailingIconRes = listOf(R.drawable.ic_check),
        onTrailingIconsClick = listOf(onContinueButtonClick)
    )

    Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding))

    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimensions.mainHorizontalPadding)
    ) {
        item {
            LabelText(
                textRes = R.string.poll_theme,
                isLarge = false
            )

            Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding / 2))

            CustomOutlinedTextField(
                value = themeInputState,
                onValueChange = {
                    isThemeInputError = it.text.isBlank()
                    themeInputState = it
                    changePollTheme(it.text)
                },
                deleteIconEnabled = false,
                trailingIconEnabled = false,
                isError = isThemeInputError && isContinueButtonClicked
            )

            Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding * 2))
        }

        itemsIndexed(pollData.questions) { questionIndex, question ->
            var questionInputState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(TextFieldValue(question.text))
            }
            val questionErrorState = rememberSaveable {
                mutableStateOf(questionInputState.text.isBlank())
            }
            questionAnswerErrorsList.add(questionErrorState)

            LabelText(
                text = stringResource(R.string.poll_question_number)
                    .format(
                        questionIndex + 1,
                        pollData.questions.size
                    ),
                isLarge = false
            )

            Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding / 2))

            CustomOutlinedTextField(
                value = questionInputState,
                onValueChange = {
                    questionErrorState.value = it.text.isBlank()
                    questionInputState = it
                    changeQuestionText(it.text, questionIndex)
                },
                onDeleteIconClick = { deleteQuestion(questionIndex) },
                trailingIconEnabled = false,
                isError = questionErrorState.value && isContinueButtonClicked
            )

            Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding))

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Dimensions.mainHorizontalPadding)
            ) {
                LabelText(
                    textRes = R.string.poll_answer_options,
                    isLarge = false
                )

                Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding / 2))

                pollData.questions[questionIndex]
                    .answerOptions
                    .forEachIndexed { answerIndex, answer ->
                        var answerInputState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                            mutableStateOf(TextFieldValue(answer.text))
                        }
                        val answerErrorState = rememberSaveable {
                            mutableStateOf(answerInputState.text.isBlank())
                        }
                        questionAnswerErrorsList.add(answerErrorState)

                        CustomOutlinedTextField(
                            value = answerInputState,
                            onValueChange = {
                                answerErrorState.value = it.text.isBlank()
                                answerInputState = it
                                changeAnswerOptionText(it.text, questionIndex, answerIndex)
                            },
                            onDeleteIconClick = {
                                deleteAnswer(questionIndex, answerIndex)
                            },
                            onTrailingIconClick = {
                                updateCorrectAnswer(questionIndex, answerIndex, true)
                            },
                            isError = answerErrorState.value && isContinueButtonClicked
                        )

                        Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding))
                    }

                TextButtonWithLeadingIcon(
                    onClick = {
                        addNewAnswerOption(questionIndex, "", false)
                    },
                    textRes = R.string.add_new_answer_option_button,
                    leadingIconRes = R.drawable.ic_add_with_circle
                )

                Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding))
            }
        }

        item {
            TextButtonWithLeadingIcon(
                onClick = { addNewQuestion("") },
                textRes = R.string.add_new_question_button,
                leadingIconRes = R.drawable.ic_add_with_circle
            )

            Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding * 2))
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFF9F9F9)
private fun NewPollScreenPreview() = VkCupTheme {
    val poll by remember{
        mutableStateOf(
            Poll(
                theme = "",
                questions = mutableListOf(
                    PollQuestion(
                        text = "",
                        answerOptions = mutableListOf(
                            AnswerOption(text = "", isCorrect = false),
                            AnswerOption(text = "", isCorrect = false)
                        )
                    )
                )
            )
        )
    }

    NewPollScreenContent(pollData = poll)
}