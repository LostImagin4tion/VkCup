package io.lostimagin4tion.vkcup.ui.screens.newFillingGaps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.getSelectedText
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import io.lostimagin4tion.vkcup.R
import io.lostimagin4tion.vkcup.domain.enitites.GapsInteractionMethod
import io.lostimagin4tion.vkcup.domain.enitites.InteractiveElementData
import io.lostimagin4tion.vkcup.domain.enitites.QuizWithGaps
import io.lostimagin4tion.vkcup.ui.components.appbars.AppBarWithMultipleButtons
import io.lostimagin4tion.vkcup.ui.components.buttons.TextFilledButton
import io.lostimagin4tion.vkcup.ui.components.chips.ChipGroup
import io.lostimagin4tion.vkcup.ui.components.chips.CustomChip
import io.lostimagin4tion.vkcup.ui.components.text.SubtitleText
import io.lostimagin4tion.vkcup.ui.components.textFields.CustomOutlinedTextField
import io.lostimagin4tion.vkcup.ui.screens.writePost.WritePostViewModel
import io.lostimagin4tion.vkcup.ui.theme.Dimensions
import io.lostimagin4tion.vkcup.ui.theme.VkCupTheme
import io.lostimagin4tion.vkcup.ui.theme.vkCupDarkGray

/**
 * [NewFillingGapsScreen] - screen for interactive element that will work like filling the gaps
 * in text using keyboard
 *
 * @author Egor Danilov
 */
@Composable
fun NewFillingGapsScreen(
    postViewModel: WritePostViewModel,
    navController: NavHostController,
    showMessage: (Int) -> Unit
) {
    val viewModel: NewFillingGapsViewModel = viewModel()
    val quizData by viewModel.quizData.collectAsState()

    NewFillingGapsScreenContent(
        quizData = quizData,
        getRememberedIndex = postViewModel::rememberedIndex::get,
        showMessage = showMessage,
        updateQuizText = viewModel::updateQuizText,
        addNewGap = viewModel::addNewGap,
        deleteGap = viewModel::deleteGap,
        finishCreatingFilling = postViewModel::addInteractiveElement,
        navigateBack = navController::popBackStack
    )
}

/**
 * [NewFillingGapsScreenContent] - layout of [NewFillingGapsScreen]
 *
 * @author Egor Danilov
 */
@Composable
private fun NewFillingGapsScreenContent(
    quizData: QuizWithGaps,
    getRememberedIndex: () -> Int = { 0 },
    showMessage: (Int) -> Unit = {},
    updateQuizText: (String) -> Unit = {},
    addNewGap: (String) -> Unit = {},
    deleteGap: (Int) -> Unit = {},
    finishCreatingFilling: (InteractiveElementData, Int) -> Unit = { _, _ -> },
    navigateBack: () -> Unit = {}
) = Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.Start,
    modifier = Modifier
        .fillMaxSize()
        .imePadding()
) {
    var questionInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var isContinueButtonClicked by rememberSaveable { mutableStateOf(false) }
    var isQuestionInputError by rememberSaveable { mutableStateOf(questionInput.text.isBlank()) }

    val onContinueButtonClick: () -> Unit = {
        isContinueButtonClicked = true

        if (questionInput.text.isBlank()) {
            showMessage(R.string.filling_creation_empty_question_error)
        }
        else if (quizData.gaps.isEmpty()) {
            showMessage(R.string.filling_creation_no_gaps_error)
        }
        else {
            finishCreatingFilling(quizData, getRememberedIndex())
            navigateBack()
        }
    }

    AppBarWithMultipleButtons(
        title = {
            SubtitleText(
                textRes = R.string.new_filling_header,
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
            { showMessage(R.string.filling_hint) },
            onContinueButtonClick
        )
    )

    Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding * 2))

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimensions.mainHorizontalPadding)
    ) {

        CustomOutlinedTextField(
            value = questionInput,
            onValueChange = {
                isQuestionInputError = it.text.isBlank()
                questionInput = it
                updateQuizText(it.text)
            },
            singleLine = false,
            deleteIconEnabled = false,
            trailingIconEnabled = false,
            shape = RoundedCornerShape(5),
            minHeight = 200.dp,
            placeholderRes = R.string.filling_question_placeholder,
            isError = isQuestionInputError && isContinueButtonClicked,
        )

        TextFilledButton(
            onClick = {
                addNewGap(questionInput.getSelectedText().text)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            textResource = R.string.add_filling_gap_button,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = Dimensions.mainVerticalPadding * 2,
                    horizontal = 36.dp
                )
        )

        ChipGroup(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = Dimensions.mainVerticalPadding)
        ) {
            quizData.gaps.forEachIndexed { index, it ->
                CustomChip(
                    label = it,
                    trailingIconId = R.drawable.ic_close,
                    onTrailingIconClick = { deleteGap(index) },
                    chipColor = vkCupDarkGray,
                    contentColor = Color.White
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFF9F9F9)
private fun NewMatchingScreenPreview() = VkCupTheme {
    val data by remember {
        mutableStateOf(QuizWithGaps(interactionMethod = GapsInteractionMethod.Filling))
    }

    NewFillingGapsScreenContent(data)
}