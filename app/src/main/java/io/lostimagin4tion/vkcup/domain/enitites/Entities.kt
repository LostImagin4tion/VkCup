package io.lostimagin4tion.vkcup.domain.enitites

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import io.lostimagin4tion.vkcup.R
import io.lostimagin4tion.vkcup.ui.components.navigation.Routes
import io.lostimagin4tion.vkcup.ui.theme.cardColors

/**
 * [Posts] used for displaying list of posts in [WelcomeScreen]
 *
 * @author Egor Danilov
 */
data class Posts(
    val posts: List<PostData> = listOf()
)

/**
 * [PostData] used for displaying specific post
 *
 * @author Egor Danilov
 */
data class PostData(
    val elements: List<InteractiveElementData> = listOf()
)

/**
 * [InteractiveElementItem] used for displaying interactive element info in [WritePostScreen]
 * bottomSheet
 *
 * @author Egor Danilov
 */
data class InteractiveElementItem(
    @StringRes val nameRes: Int,
    @DrawableRes val iconRes: Int,
    val backgroundColor: Color,
    val route: String
)

val interactiveElementItems = listOf(
    InteractiveElementItem(
        nameRes = R.string.text,
        iconRes = R.drawable.ic_text,
        backgroundColor = cardColors[0],
        route = "",
        ),
    InteractiveElementItem(
        nameRes = R.string.poll,
        iconRes = R.drawable.ic_poll,
        backgroundColor = cardColors[1],
        route = Routes.newPollElement
    ),
    InteractiveElementItem(
        nameRes = R.string.matching,
        iconRes = R.drawable.ic_matching,
        backgroundColor = cardColors[2],
        route = Routes.newMatchingElement
        ),
    InteractiveElementItem(
        nameRes = R.string.dragging_into_gaps,
        iconRes = R.drawable.ic_dragging,
        backgroundColor = cardColors[3],
        route = Routes.newDraggingElement
        ),
    InteractiveElementItem(
        nameRes = R.string.fill_the_gaps,
        iconRes = R.drawable.ic_fill_the_gap,
        backgroundColor = cardColors[4],
        route = Routes.newFillTheGapsElement
        ),
    InteractiveElementItem(
        nameRes = R.string.rating,
        iconRes = R.drawable.ic_star_filled,
        backgroundColor = cardColors[5],
        route = Routes.newRatingElement
    )
)

/**
 * [InteractiveElementData] - sealed class that is used for storing all interactive elements data
 * in [PostData] class
 *
 * @author Egor Danilov
 */
sealed class InteractiveElementData

/**
 * [Text] - data class for Text interactive element
 *
 * @author Egor Danilov
 */
data class Text(
    val text: String = ""
): InteractiveElementData()

/**
 * [Poll] - data class for Poll interactive element
 *
 * @author Egor Danilov
 */
data class Poll(
    val theme: String = "",
    val questions: List<PollQuestion> = listOf()
): InteractiveElementData()

/**
 * [PollQuestion] - data about question in [Poll]
 *
 * @author Egor Danilov
 */
data class PollQuestion(
    val text: String,
    val answerOptions: List<AnswerOption>
): InteractiveElementData()

/**
 * [AnswerOption] - data about answer option in [PollQuestion]
 *
 * @author Egor Danilov
 */
data class AnswerOption(
    val text: String = "",
    val isCorrect: Boolean = false
): InteractiveElementData()

/**
 * [StarRating] - data class for Rating interactive element
 *
 * @author Egor Danilov
 */
data class StarRating(
    val size: Int = 5,
    val clickedStarIndex: Int = 0
): InteractiveElementData()

/**
 * [Matching] - data class for "Matching items between two columns" interactive element
 *
 * @author Egor Danilov
 */
data class Matching(
    val items: List<QuestionAnswerPair> = listOf()
): InteractiveElementData()

/**
 * [Matching] - data about question and answer columns in [Matching]
 *
 * @author Egor Danilov
 */
data class QuestionAnswerPair(
    val question: String = "",
    val answer: String = ""
): InteractiveElementData()

/**
 * [QuizWithGaps] - data class for "Dragging options into gaps in text" and
 * "Filling in a gap in the text" interactive elements
 *
 * @author Egor Danilov
 */
data class QuizWithGaps(
    val text: String = "",
    val gaps: List<String> = listOf(),
    val interactionMethod: GapsInteractionMethod
): InteractiveElementData()

enum class GapsInteractionMethod {
    Dragging,
    Filling
}

/**
 * [ConvertedQuizWithGaps] - data class for convenient displaying of data in
 * [DraggingIntoGapsInteractiveElement] and [FillingGapsInteractiveElement]
 * after processing
 */
data class ConvertedQuizWithGaps(
    val textItems: List<Pair<String, Boolean>> = listOf(),
    val gapsWithIndex: List<Pair<String, Int>> = listOf()
)