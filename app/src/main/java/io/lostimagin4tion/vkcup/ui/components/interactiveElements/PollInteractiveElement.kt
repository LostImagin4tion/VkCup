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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.lostimagin4tion.vkcup.R
import io.lostimagin4tion.vkcup.domain.enitites.Poll
import io.lostimagin4tion.vkcup.ui.components.text.HeadlineText
import io.lostimagin4tion.vkcup.ui.components.text.LabelText
import io.lostimagin4tion.vkcup.ui.theme.Dimensions
import io.lostimagin4tion.vkcup.ui.theme.vkCupOrange

/**
 * [PollInteractiveElement] - layout of "Poll" interactive element
 * for displaying it in [WatchScreen]
 *
 * @author Egor Danilov
 */
@Composable
fun PollInteractiveElement(
    data: Poll
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
    Spacer(modifier = Modifier.height(Dimensions.commonPadding))

    LabelText(
        text = data.theme,
        isLarge = true,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(horizontal = Dimensions.commonPadding)
    )

    Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding * 2))

    data.questions.forEachIndexed { questionIndex, question ->
        HeadlineText(
            text = stringResource(R.string.poll_question_number)
                .format(
                    questionIndex + 1,
                    data.questions.size
                ),
            isLarge = false,
            modifier = Modifier.padding(start = Dimensions.commonPadding)
        )

        LabelText(
            text = question.text,
            isLarge = true,
            modifier = Modifier.padding(start = Dimensions.commonPadding)
        )

        Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding))

        var isAnswerClicked by rememberSaveable { mutableStateOf(false) }

        data.questions[questionIndex]
            .answerOptions
            .forEach { answer ->
                var isClicked by rememberSaveable { mutableStateOf(false) }

                val transition = updateTransition(
                    targetState = answer.isCorrect,
                    label = "colorTransition"
                )

                val backgroundColor by transition.animateColor(
                    label = "backgroundTransition",
                    transitionSpec = { spring() }
                ) { isCorrect ->
                    if (isCorrect && isClicked) {
                        MaterialTheme.colorScheme.tertiary
                    } else if (isClicked) {
                        vkCupOrange
                    } else {
                        MaterialTheme.colorScheme.tertiaryContainer
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20))
                        .background(
                            color = backgroundColor,
                            shape = RoundedCornerShape(20)
                        )
                        .clickable {
                            if (!isAnswerClicked) {
                                isAnswerClicked = true
                                isClicked = true
                            }
                        }
                ) {
                    HeadlineText(
                        text = answer.text,
                        isLarge = true,
                        modifier = Modifier
                            .padding(start = Dimensions.commonPadding)
                            .padding(vertical = Dimensions.mainVerticalPadding)
                    )
                }

                Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding / 2))
            }

        Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding))
    }
}