package io.lostimagin4tion.vkcup.data.repositories

import io.lostimagin4tion.vkcup.domain.enitites.QuizWithGaps
import io.lostimagin4tion.vkcup.domain.repositories.IDraggingIntoGapsRepository
import io.lostimagin4tion.vkcup.utils.withIO
import javax.inject.Inject

/**
 * [DraggingIntoGapsRepository] - repository that is used data manipulation of [NewDraggingIntoGapsScreen]
 *
 * @see [IDraggingIntoGapsRepository]
 *
 * @author Egor Danilov
 */
class DraggingIntoGapsRepository @Inject constructor(): IDraggingIntoGapsRepository {

    override suspend fun updateQuizText(quizData: QuizWithGaps, text: String) = withIO {
        quizData.copy(text = text)
    }

    override suspend fun addNewGap(quizData: QuizWithGaps, text: String) = withIO {
        quizData.copy(
            gaps = quizData.gaps + listOf(text)
        )
    }

    override suspend fun deleteGap(quizData: QuizWithGaps, index: Int) = withIO {
        val newList = mutableListOf<String>()
        newList.addAll(quizData.gaps)
        newList.removeAt(index)

        quizData.copy(
            gaps = newList
        )
    }
}