package io.lostimagin4tion.vkcup.ui.components.textFields

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.with
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.lostimagin4tion.vkcup.R
import io.lostimagin4tion.vkcup.ui.components.text.LabelText
import io.lostimagin4tion.vkcup.ui.theme.Dimensions
import io.lostimagin4tion.vkcup.ui.theme.vkCupOrange
import io.lostimagin4tion.vkcup.ui.theme.vkCupGray

/**
 * [CustomOutlinedTextField] - text field that supports custom content padding, trailing icon,
 * placeholder and deleteIcon on the right of text field.
 * As default Material text fields doesnt support custom content paddings, I had to implement my own
 *
 * @author Egor Danilov
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun CustomOutlinedTextField(
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    minHeight: Dp? = null,
    @StringRes placeholderRes: Int? = null,
    isError: Boolean = false,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    deleteIconEnabled: Boolean = true,
    trailingIconEnabled: Boolean = true,
    onDeleteIconClick: () -> Unit = {},
    onValueChange: (TextFieldValue) -> Unit = {},
    onTrailingIconClick: () -> Unit = {},
    shape: CornerBasedShape = MaterialTheme.shapes.small,
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    var isAnswerCorrect by rememberSaveable { mutableStateOf(false) }
    interactionSource.interactions

    val trailingIconSize = 20.dp
    val deleteIconSize = 36.dp
    val contentVerticalPadding = if (trailingIconEnabled) 4.dp else 12.dp

    val transition = updateTransition(
        targetState = isAnswerCorrect,
        label = "colorTransition"
    )

    val unfocusedBorderColor by transition.animateColor(
        label = "unfocusedBorderColor",
        transitionSpec = { spring() }
    ) { isCorrect ->
        if (isCorrect) {
            MaterialTheme.colorScheme.tertiary
        } else {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
        }
    }

    val trailingIconColor by transition.animateColor(
        label = "trailingIconColor",
        transitionSpec = { spring() }
    ) { isCorrect ->
        if (isCorrect) {
            MaterialTheme.colorScheme.tertiary
        } else {
            MaterialTheme.colorScheme.primary
        }
    }

    val trailingIcon = @Composable {
        AnimatedContent(
            targetState = isAnswerCorrect,
            transitionSpec = {
                scaleIn() with fadeOut()
            }
        ) { isCorrect ->
            if (isCorrect) {
                Icon(
                    painter = painterResource(R.drawable.ic_check_with_circle),
                    contentDescription = null,
                    modifier = Modifier.size(trailingIconSize),
                    tint = trailingIconColor
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.ic_circle),
                    contentDescription = null,
                    modifier = Modifier.size(trailingIconSize),
                    tint = trailingIconColor
                )
            }
        }
    }

    val colors = TextFieldDefaults.outlinedTextFieldColors(
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        textColor = MaterialTheme.colorScheme.primary,
        cursorColor = MaterialTheme.colorScheme.primary,
        focusedBorderColor = vkCupOrange,
        unfocusedBorderColor = unfocusedBorderColor,
        errorBorderColor = MaterialTheme.colorScheme.error,
        errorCursorColor = MaterialTheme.colorScheme.error,
        errorTrailingIconColor = MaterialTheme.colorScheme.error,
        errorLabelColor = MaterialTheme.colorScheme.error
    )

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        var fieldModifier = Modifier
            .fillMaxWidth()
            .weight(1f)

        minHeight?.let {
            fieldModifier = fieldModifier.defaultMinSize(minHeight = it)
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.primary
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            enabled = enabled,
            singleLine = singleLine,
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }),
            modifier = fieldModifier,
        ) { innerTextField ->
            TextFieldDefaults.OutlinedTextFieldDecorationBox(
                value = value.text,
                innerTextField = innerTextField,
                enabled = enabled,
                singleLine = singleLine,
                visualTransformation = visualTransformation,
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = contentVerticalPadding
                ),
                interactionSource = interactionSource,
                placeholder = placeholderRes?.let {
                    {
                        LabelText(
                            textRes = it,
                            textColor = vkCupGray,
                            isLarge = false
                        )
                    }
                },
                trailingIcon = if (trailingIconEnabled) {
                    {
                        AnimatedVisibility(
                            visible = value.text.isNotEmpty(),
                            enter = scaleIn(),
                            exit = scaleOut()
                        ) {
                            IconButton(
                                onClick = {
                                    onTrailingIconClick()
                                    isAnswerCorrect = !isAnswerCorrect
                                },
                                modifier = Modifier.size(trailingIconSize)
                            ) {
                                trailingIcon()
                            }
                        }
                    }
                } else null,
                colors = colors,
                container = {
                    TextFieldDefaults.OutlinedBorderContainerBox(
                        enabled = enabled,
                        isError = isError,
                        interactionSource =  interactionSource,
                        shape = shape,
                        colors = colors,
                        unfocusedBorderThickness = 2.dp,
                        focusedBorderThickness = 2.dp
                    )
                }
            )
        }

        if (deleteIconEnabled) {
            IconButton(
                onClick = onDeleteIconClick,
                modifier = Modifier
                    .size(deleteIconSize)
                    .padding(start = Dimensions.mainHorizontalPadding / 2)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(deleteIconSize)
                        .weight(1f)
                )
            }
        }
    }
}