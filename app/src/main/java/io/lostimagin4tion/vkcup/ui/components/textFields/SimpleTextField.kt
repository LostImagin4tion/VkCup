package io.lostimagin4tion.vkcup.ui.components.textFields

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import io.lostimagin4tion.vkcup.ui.theme.vkCupGray

/**
 * [SimpleTextField] is simple text field without icons and background
 *
 * @author Egor Danilov
 */
@Composable
fun SimpleTextField(
    value: TextFieldValue,
    modifier: Modifier = Modifier.fillMaxWidth(),
    onValueChange: (TextFieldValue) -> Unit = {},
    @StringRes placeholderRes: Int,
    textColor: Color = MaterialTheme.colorScheme.primary,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    val focusManager = LocalFocusManager.current

    Box(modifier = Modifier.wrapContentSize()) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                textColor
            ),
            enabled = enabled,
            singleLine = singleLine,
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            modifier = modifier
        ) { innerTextField ->
            Box {
                if (value.text.isBlank()) {
                    Text(
                        text = stringResource(placeholderRes),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Start,
                        color = vkCupGray,
                    )
                }

                innerTextField()
            }
        }
    }
}