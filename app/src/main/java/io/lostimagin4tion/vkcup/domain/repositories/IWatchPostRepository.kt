package io.lostimagin4tion.vkcup.domain.repositories

import io.lostimagin4tion.vkcup.domain.enitites.ConvertedQuizWithGaps
import io.lostimagin4tion.vkcup.domain.enitites.QuizWithGaps

interface IWatchPostRepository {

    fun convertQuizWithGaps(quizWithGaps: QuizWithGaps): ConvertedQuizWithGaps
}