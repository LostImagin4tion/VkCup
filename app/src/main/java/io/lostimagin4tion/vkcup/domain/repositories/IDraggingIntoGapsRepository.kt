package io.lostimagin4tion.vkcup.domain.repositories

import io.lostimagin4tion.vkcup.domain.enitites.QuizWithGaps

/**
 * [IDraggingIntoGapsRepository] - interface for description of [DraggingIntoGapsRepository]
 * repository that is used data manipulation of [NewDraggingIntoGapsScreen]
 *
 * @author Egor Danilov
 */
interface IDraggingIntoGapsRepository {

    suspend fun updateQuizText(quizData: QuizWithGaps, text: String): QuizWithGaps

    suspend fun addNewGap(quizData: QuizWithGaps, text: String): QuizWithGaps
    suspend fun deleteGap(quizData: QuizWithGaps, index: Int): QuizWithGaps
}