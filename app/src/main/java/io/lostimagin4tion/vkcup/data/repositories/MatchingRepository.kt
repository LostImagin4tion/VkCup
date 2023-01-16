package io.lostimagin4tion.vkcup.data.repositories

import io.lostimagin4tion.vkcup.domain.enitites.Matching
import io.lostimagin4tion.vkcup.domain.enitites.QuestionAnswerPair
import io.lostimagin4tion.vkcup.domain.repositories.IDraggingIntoGapsRepository
import io.lostimagin4tion.vkcup.domain.repositories.IMatchingRepository
import io.lostimagin4tion.vkcup.utils.withIO
import javax.inject.Inject

/**
 * [MatchingRepository] - repository that is used data manipulation of [NewMatchingScreen]
 *
 * @see [IMatchingRepository]
 *
 * @author Egor Danilov
 */
class MatchingRepository @Inject constructor(): IMatchingRepository {

    override suspend fun addQuestionAnswerPair(matching: Matching) = withIO {
        matching.copy(
            items = matching.items + listOf(QuestionAnswerPair())
        )
    }

    override suspend fun deleteQuestionAnswerPair(matching: Matching, index: Int) = withIO {
        val newList = mutableListOf<QuestionAnswerPair>()
        newList.addAll(matching.items)
        newList.removeAt(index)

        matching.copy(items = newList)
    }

    override suspend fun changeQuestionText(
        matching: Matching,
        text: String,
        index: Int
    ) = withIO {
        val newList = mutableListOf<QuestionAnswerPair>()
        newList.addAll(matching.items)

        val changedPair = matching
            .items[index]
            .copy(question = text)

        newList[index] = changedPair

        matching.copy(items = newList)
    }

    override suspend fun changeAnswerText(
        matching: Matching,
        text: String,
        index: Int
    ) = withIO {
        val newList = mutableListOf<QuestionAnswerPair>()
        newList.addAll(matching.items)

        val changedPair = matching
            .items[index]
            .copy(answer = text)

        newList[index] = changedPair

        matching.copy(items = newList)
    }
}