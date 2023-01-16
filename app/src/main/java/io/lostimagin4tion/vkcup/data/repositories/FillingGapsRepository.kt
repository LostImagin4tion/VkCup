package io.lostimagin4tion.vkcup.data.repositories

import io.lostimagin4tion.vkcup.domain.enitites.QuizWithGaps
import io.lostimagin4tion.vkcup.domain.repositories.IDraggingIntoGapsRepository
import io.lostimagin4tion.vkcup.domain.repositories.IFillingGapsRepository
import io.lostimagin4tion.vkcup.utils.withIO
import javax.inject.Inject

/**
 * [FillingGapsRepository] - repository that is used data manipulation of [NewFillingGapsScreen]
 *
 * @see [IFillingGapsRepository]
 *
 * @author Egor Danilov
 */
class FillingGapsRepository @Inject constructor(): IFillingGapsRepository {

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