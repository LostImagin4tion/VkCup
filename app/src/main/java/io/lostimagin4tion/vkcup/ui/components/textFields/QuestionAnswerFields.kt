package io.lostimagin4tion.vkcup.ui.components.textFields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import io.lostimagin4tion.vkcup.R
import io.lostimagin4tion.vkcup.ui.theme.Dimensions

/**
 * [QuestionAnswerFields] - pair of question and answer text fields, used in [NewMatchingScreen]
 *
 * @author Egor Danilov
 */
@Composable
fun QuestionAnswerFields(
    questionState: TextFieldValue,
    answerState: TextFieldValue,
    onQuestionValueChange: (TextFieldValue) -> Unit = {},
    onAnswerValueChange: (TextFieldValue) -> Unit = {},
    onDeleteIconClick: () -> Unit = {},
    isQuestionError: Boolean,
    isAnswerError: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomOutlinedTextField(
            value = questionState,
            onValueChange = onQuestionValueChange,
            deleteIconEnabled = false,
            trailingIconEnabled = false,
            shape = RoundedCornerShape(50),
            modifier = Modifier.weight(1f),
            isError = isQuestionError
        )

        Spacer(modifier = Modifier.width(Dimensions.mainHorizontalPadding))

        CustomOutlinedTextField(
            value = answerState,
            onValueChange = onAnswerValueChange,
            deleteIconEnabled = false,
            trailingIconEnabled = false,
            shape = RoundedCornerShape(20),
            modifier = Modifier.weight(1f),
            isError = isAnswerError
        )

        Spacer(modifier = Modifier.width(Dimensions.mainHorizontalPadding / 2))

        IconButton(
            onClick = onDeleteIconClick,
            modifier = Modifier.size(26.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(24.dp)
                    .weight(1f)
            )
        }
    }
}