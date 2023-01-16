package io.lostimagin4tion.vkcup.ui.components.contentBlocks

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.lostimagin4tion.vkcup.R
import io.lostimagin4tion.vkcup.ui.components.buttons.TextButtonWithLeadingIcon
import io.lostimagin4tion.vkcup.ui.theme.Dimensions

/**
 * [DefaultBlock] - block of content (e.g. [InteractiveElementSmallCard]) and
 * two "Add Block" buttons above and below it
 */
@Composable
fun DefaultBlock(
    onFirstAddBlockClick: () -> Unit = {},
    onSecondAddBlockClick: () -> Unit = {},
    middleContent: @Composable () -> Unit
) {
    TextButtonWithLeadingIcon(
        textRes = R.string.add_new_content_block_button,
        leadingIconRes = R.drawable.ic_add_with_circle,
        onClick = onFirstAddBlockClick
    )

    Spacer(modifier = Modifier.height(Dimensions.commonPadding))

    middleContent()

    Spacer(modifier = Modifier.height(Dimensions.commonPadding))

    TextButtonWithLeadingIcon(
        textRes = R.string.add_new_content_block_button,
        leadingIconRes = R.drawable.ic_add_with_circle,
        onClick = onSecondAddBlockClick
    )
}

/**
 * [ClosingBlock] - block of content (e.g. [InteractiveElementSmallCard]) and
 * "Add Block" button below it
 */
@Composable
fun ClosingBlock(
    onAddBlockClick: () -> Unit = {},
    endContent: @Composable () -> Unit
) {
    endContent()

    Spacer(modifier = Modifier.height(Dimensions.commonPadding))

    TextButtonWithLeadingIcon(
        textRes = R.string.add_new_content_block_button,
        leadingIconRes = R.drawable.ic_add_with_circle,
        onClick = onAddBlockClick
    )
}