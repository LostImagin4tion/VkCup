package io.lostimagin4tion.vkcup.ui.components.interactiveElements

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import io.lostimagin4tion.vkcup.R
import io.lostimagin4tion.vkcup.domain.enitites.ConvertedQuizWithGaps
import io.lostimagin4tion.vkcup.domain.enitites.QuizWithGaps
import io.lostimagin4tion.vkcup.ui.components.buttons.TextFilledButton
import io.lostimagin4tion.vkcup.ui.components.chips.ChipGroup
import io.lostimagin4tion.vkcup.ui.components.chips.CustomChip
import io.lostimagin4tion.vkcup.ui.components.dragAndDrop.DragTarget
import io.lostimagin4tion.vkcup.ui.components.dragAndDrop.DraggableBox
import io.lostimagin4tion.vkcup.ui.components.dragAndDrop.DropItem
import io.lostimagin4tion.vkcup.ui.components.text.LabelText
import io.lostimagin4tion.vkcup.ui.theme.Dimensions
import io.lostimagin4tion.vkcup.ui.theme.vkCupDarkGray
import io.lostimagin4tion.vkcup.ui.theme.vkCupError
import io.lostimagin4tion.vkcup.ui.theme.vkCupHighlight
import io.lostimagin4tion.vkcup.ui.theme.vkCupOrange

/**
 * [DraggingIntoGapsInteractiveElement] - layout of "Dragging into Gaps" interactive element
 * for displaying it in [WatchScreen]
 *
 * @author Egor Danilov
 */
@Composable
fun DraggingIntoGapsInteractiveElement(
    data: ConvertedQuizWithGaps,
    modifier: Modifier = Modifier
) = DraggableBox {
    val defaultGapText = stringResource(R.string.filling_gaps_placeholder)

    var isSubmitButtonClicked by rememberSaveable { mutableStateOf(false) }
    val textFieldStates = mutableListOf<MutableState<TextFieldValue>>()

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(0.25f),
                shape = RoundedCornerShape(10)
            )
            .padding(
                vertical = Dimensions.commonPadding * 2,
                horizontal = Dimensions.mainHorizontalPadding / 2
            )
    ) {
        val relativeOffset = Offset(x = 0f, y = 250f)

        LabelText(
            textRes = R.string.dragging_into_gaps_element_header,
            isLarge = true,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = Dimensions.commonPadding)
        )

        Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding))

        FlowRow(
            mainAxisAlignment = FlowMainAxisAlignment.Start,
            mainAxisSpacing = 3.dp,
            crossAxisSpacing = Dimensions.commonPadding / 2,
            crossAxisAlignment = FlowCrossAxisAlignment.Start
        ) {
            data.textItems.forEach { item ->
                if (item.second) {
                    val inputState = rememberSaveable(stateSaver = TextFieldValue.Saver) {
                        mutableStateOf(TextFieldValue())
                    }
                    textFieldStates.add(inputState)

                    val transition = updateTransition(
                        targetState = inputState.value.text.trim() == item.first.trim(),
                        label = "colorTransition"
                    )

                    val textColor by transition.animateColor(
                        label = "backgroundTransition",
                        transitionSpec = { spring() }
                    ) { isCorrect ->
                        if (isCorrect && isSubmitButtonClicked) {
                            MaterialTheme.colorScheme.tertiary
                        } else if (!isCorrect && isSubmitButtonClicked) {
                            vkCupError
                        } else {
                            vkCupOrange
                        }
                    }

                    DropItem<String>(
                        relativeOffset = relativeOffset
                    ) { isInBound, data ->
                        if (data != null) {
                            LaunchedEffect(key1 = data) {
                                inputState.value = TextFieldValue(data)
                            }
                        }
                        LabelText(
                            text = inputState.value.text.ifBlank {
                                defaultGapText
                            },
                            textColor = if (isInBound) vkCupHighlight else textColor
                        )
                    }
                } else {
                    LabelText(
                        text = item.first
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(Dimensions.mainVerticalPadding * 2))

        LabelText(
            textRes = R.string.dragging_answer_options,
            isLarge = true
        )

        Spacer(modifier = Modifier.height(Dimensions.commonPadding))

        ChipGroup(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimensions.mainVerticalPadding)
        ) {
            data.gapsWithIndex.forEach {
                DragTarget(
                    dataToDrop = it.first,
                    relativeOffset = relativeOffset,
                    onDragStart = { isSubmitButtonClicked = false }
                ) {
                    CustomChip(
                        label = it.first,
                        chipColor = vkCupDarkGray,
                        contentColor = Color.White
                    )
                }

            }
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
                    horizontal = Dimensions.mainHorizontalPadding * 2
                )
        )
    }
}