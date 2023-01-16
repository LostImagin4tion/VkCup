package io.lostimagin4tion.vkcup.ui.components.interactiveElements

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import io.lostimagin4tion.vkcup.domain.enitites.StarRating
import io.lostimagin4tion.vkcup.ui.components.buttons.StarButton
import io.lostimagin4tion.vkcup.ui.theme.Dimensions

/**
 * [RatingInteractiveElement] - layout of "Rating" interactive element
 * for displaying it in [WatchScreen]
 *
 * @author Egor Danilov
 */
@Composable
fun RatingInteractiveElement(
    data: StarRating,
    modifier: Modifier = Modifier
) {
    var clickedStarIndex by rememberSaveable { mutableStateOf(data.clickedStarIndex) }

    FlowRow(
        mainAxisSize = SizeMode.Expand,
        mainAxisAlignment = FlowMainAxisAlignment.Center,
        mainAxisSpacing = Dimensions.commonPadding,
        crossAxisAlignment = FlowCrossAxisAlignment.Center,
        crossAxisSpacing = Dimensions.commonPadding,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(0.25f),
                shape = RoundedCornerShape(30)
            )
            .padding(vertical = Dimensions.commonPadding)
    ) {
        repeat(data.size) { index ->
            StarButton(
                onClick = {
                    clickedStarIndex = index
                },
                isFilled = index < clickedStarIndex + 1,
            )
        }
    }
}