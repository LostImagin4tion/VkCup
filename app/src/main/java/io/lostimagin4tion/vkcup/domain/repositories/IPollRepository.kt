package io.lostimagin4tion.vkcup.domain.repositories

import io.lostimagin4tion.vkcup.domain.enitites.Poll

/**
 * [IPollRepository] - interface for description of [PollRepository]
 * repository that is used data manipulation of [NewPollScreen]
 *
 * @author Egor Danilov
 */
interface IPollRepository {

    suspend fun addNewQuestion(poll: Poll, text: String): Poll

    suspend fun addNewAnswerOption(
        poll: Poll,
        questionIndex: Int,
        text: String,
        isCorrect: Boolean
    ): Poll

    suspend fun changePollTheme(poll: Poll, text: String): Poll

    suspend fun changeQuestionText(
        poll: Poll,
        text: String,
        questionIndex: Int
    ): Poll

    suspend fun changeAnswerOptionText(
        poll: Poll,
        text: String,
        questionIndex: Int,
        answerIndex: Int
    ): Poll

    suspend fun updateCorrectAnswer(
        poll: Poll,
        questionIndex: Int,
        answerIndex: Int,
        isCorrect: Boolean
    ): Poll

    suspend fun deleteQuestion(poll: Poll, questionIndex: Int): Poll

    suspend fun deleteAnswerOption(
        poll: Poll,
        questionIndex: Int,
        answerIndex: Int
    ): Poll
}