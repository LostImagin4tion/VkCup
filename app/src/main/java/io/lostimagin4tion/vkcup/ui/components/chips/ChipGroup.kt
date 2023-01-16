package io.lostimagin4tion.vkcup.ui.components.chips

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import io.lostimagin4tion.vkcup.ui.theme.Dimensions


/**
 * [ChipGroup] - grid of toggleable chips with optional content below
 *
 * @author Egor Danilov
 */
@Composable
fun ChipGroup(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) = FlowRow(
    mainAxisSpacing = Dimensions.chipGroupSpacing,
    mainAxisAlignment = FlowMainAxisAlignment.Start,
    crossAxisSpacing = Dimensions.chipGroupSpacing,
    crossAxisAlignment = FlowCrossAxisAlignment.Start,
    modifier = modifier
) {
    content()
}