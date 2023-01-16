package io.lostimagin4tion.vkcup.domain.repositories

import io.lostimagin4tion.vkcup.domain.enitites.QuizWithGaps

/**
 * [IFillingGapsRepository] - interface for description of [FillingGapsRepository]
 * repository that is used data manipulation of [NewFillingGapsScreen]
 *
 * @author Egor Danilov
 */
interface IFillingGapsRepository {

    suspend fun updateQuizText(quizData: QuizWithGaps, text: String): QuizWithGaps

    suspend fun addNewGap(quizData: QuizWithGaps, text: String): QuizWithGaps
    suspend fun deleteGap(quizData: QuizWithGaps, index: Int): QuizWithGaps
}