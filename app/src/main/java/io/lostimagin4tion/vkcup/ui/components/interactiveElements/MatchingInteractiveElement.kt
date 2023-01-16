package io.lostimagin4tion.vkcup.ui.components.interactiveElements

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.lostimagin4tion.vkcup.R
import io.lostimagin4tion.vkcup.domain.enitites.Matching
import io.lostimagin4tion.vkcup.ui.components.buttons.TextFilledButton
import io.lostimagin4tion.vkcup.ui.components.text.LabelText
import io.lostimagin4tion.vkcup.ui.theme.Dimensions
import io.lostimagin4tion.vkcup.ui.theme.vkCupError

/**
 * [MatchingInteractiveElement] - layout of "Matching Element" interactive element
 * for displaying it in [WatchScreen]
 *
 * @author Egor Danilov
 */
@Composable
fun MatchingInteractiveElement(
    data: Matching
) = Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.Start,
    modifier = Modifier
        .fillMaxWidth()
        .border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary.copy(0.25f),
            shape = RoundedCornerShape(10)
        )
        .padding(
            vertical = Dimensions.commonPadding,
            horizontal = Dimensions.commonPadding
        )
) {
    val answers = List(data.items.size) { i -> data.items[i].answer }

    var selectedAnswers by rememberSaveable { mutableStateOf(List(data.items.size) { "" }) }
    var isSubmitButtonClicked by rememberSaveable { mutableStateOf(false) }

    Spacer(modifier = Modifier.height(Dimensions.commonPadding))

    LabelText(
        textRes = R.string.matching_element_header,
        isLarge = true,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(horizontal = Dimensions.commonPadding)
    )

    Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding * 2))

    data.items.forEachIndexed { questionIndex, item ->
        val transition = updateTransition(
            targetState = selectedAnswers[questionIndex].trim() == answers[questionIndex].trim(),
            label = "colorTransition"
        )

        val backgroundColor by transition.animateColor(
            label = "backgroundTransition",
            transitionSpec = { spring() }
        ) { isCorrect ->
            if (isCorrect && isSubmitButtonClicked) {
                MaterialTheme.colorScheme.tertiary
            } else if (!isCorrect && isSubmitButtonClicked) {
                vkCupError
            } else {
                MaterialTheme.colorScheme.primary
            }
        }

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimensions.commonPadding / 2)
        ) {
            LabelText(
                text = item.question,
                textAlign = TextAlign.Start,
                textColor = MaterialTheme.colorScheme.primary,
                isLarge = true,
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary.copy(0.5f),
                        shape = RoundedCornerShape(20)
                    )
                    .padding(Dimensions.commonPadding)
            )

            Spacer(modifier = Modifier.height(Dimensions.commonPadding))

            val defaultText = stringResource(R.string.matching_answer_button_default)
            var answerState by rememberSaveable { mutableStateOf(defaultText) }

            var isMenuExpanded by remember { mutableStateOf(false) }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Dimensions.mainHorizontalPadding)
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LabelText(
                        text = answerState,
                        textAlign = TextAlign.Center,
                        textColor = MaterialTheme.colorScheme.onPrimary,
                        isLarge = false,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20))
                            .background(
                                color = backgroundColor,
                                shape = RoundedCornerShape(20)
                            )
                            .padding(Dimensions.commonPadding)
                            .clickable { isMenuExpanded = true }
                    )
                    
                    Spacer(modifier = Modifier.height(Dimensions.commonPadding))

                    DropdownMenu(
                        expanded = isMenuExpanded,
                        onDismissRequest = { isMenuExpanded = false },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        answers.forEachIndexed { index, item ->
                            DropdownMenuItem(
                                text = { LabelText(text = item) },
                                onClick = {
                                    isSubmitButtonClicked = false

                                    val newList = mutableListOf<String>()
                                    newList.addAll(selectedAnswers)
                                    newList[questionIndex] = item

                                    selectedAnswers = newList
                                    answerState = item
                                    isMenuExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(Dimensions.commonPadding))
    }

    TextFilledButton(
        onClick = {
            isSubmitButtonClicked = true
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        textResource = R.string.matching_submit,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = Dimensions.mainVerticalPadding,
                horizontal = 36.dp
            )
    )
}