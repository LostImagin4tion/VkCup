package io.lostimagin4tion.vkcup.data.repositories

import io.lostimagin4tion.vkcup.domain.enitites.AnswerOption
import io.lostimagin4tion.vkcup.domain.enitites.Poll
import io.lostimagin4tion.vkcup.domain.enitites.PollQuestion
import io.lostimagin4tion.vkcup.domain.repositories.IDraggingIntoGapsRepository
import io.lostimagin4tion.vkcup.domain.repositories.IPollRepository
import io.lostimagin4tion.vkcup.utils.withIO
import javax.inject.Inject

/**
 * [PollRepository] - repository that is used data manipulation of [NewPollScreen]
 *
 * @see [IPollRepository]
 *
 * @author Egor Danilov
 */
class PollRepository @Inject constructor(): IPollRepository {

    override suspend fun addNewQuestion(poll: Poll, text: String) = withIO {
        poll.copy(
            questions = poll.questions + listOf(
                PollQuestion(
                    text = "",
                    answerOptions = mutableListOf(
                        AnswerOption(text = "", isCorrect = false),
                        AnswerOption(text = "", isCorrect = false)
                    )
                )
            )
        )
    }

    override suspend fun addNewAnswerOption(
        poll: Poll,
        questionIndex: Int,
        text: String,
        isCorrect: Boolean
    ) = withIO {
        val newQuestionList = mutableListOf<PollQuestion>()
        newQuestionList.addAll(poll.questions)

        newQuestionList[questionIndex] = newQuestionList[questionIndex].copy(
            text = newQuestionList[questionIndex].text,
            answerOptions = newQuestionList[questionIndex].answerOptions + listOf(AnswerOption())
        )

        poll.copy(questions = newQuestionList)
    }

    override suspend fun changePollTheme(poll: Poll, text: String) = withIO {
        poll.copy(theme = text)
    }

    override suspend fun changeQuestionText(poll: Poll, text: String, questionIndex: Int) = withIO {
        val changedQuestion = poll.questions[questionIndex].copy(text = text)

        val newQuestionList = mutableListOf<PollQuestion>()
        newQuestionList.addAll(poll.questions)

        newQuestionList[questionIndex] = changedQuestion

        poll.copy(questions = newQuestionList)
    }

    override suspend fun changeAnswerOptionText(
        poll: Poll,
        text: String,
        questionIndex: Int,
        answerIndex: Int
    ) = withIO {
        val changedAnswer = poll
            .questions[questionIndex]
            .answerOptions[answerIndex]
            .copy(text = text)

        val newAnswerList = mutableListOf<AnswerOption>()
        newAnswerList.addAll(poll.questions[questionIndex].answerOptions)
        newAnswerList[answerIndex] = changedAnswer

        val newQuestionList = mutableListOf<PollQuestion>()
        newQuestionList.addAll(poll.questions)
        newQuestionList[questionIndex] = newQuestionList[questionIndex].copy(
            text = newQuestionList[questionIndex].text,
            answerOptions = newAnswerList
        )

        poll.copy(questions = newQuestionList)
    }

    override suspend fun updateCorrectAnswer(
        poll: Poll,
        questionIndex: Int,
        answerIndex: Int,
        isCorrect: Boolean
    ) = withIO {
        val changedAnswer = poll
            .questions[questionIndex]
            .answerOptions[answerIndex]
            .copy(isCorrect = isCorrect)

        val newAnswerList = mutableListOf<AnswerOption>()
        newAnswerList.addAll(poll.questions[questionIndex].answerOptions)
        newAnswerList[answerIndex] = changedAnswer

        val newQuestionList = mutableListOf<PollQuestion>()
        newQuestionList.addAll(poll.questions)
        newQuestionList[questionIndex] = newQuestionList[questionIndex].copy(
            text = newQuestionList[questionIndex].text,
            answerOptions = newAnswerList
        )

        poll.copy(questions = newQuestionList)
    }


    override suspend fun deleteQuestion(poll: Poll, questionIndex: Int) = withIO {
        val newQuestionsList = mutableListOf<PollQuestion>()
        newQuestionsList.addAll(poll.questions)
        newQuestionsList.removeAt(questionIndex)

        poll.copy(questions = newQuestionsList)
    }

    override suspend fun deleteAnswerOption(
        poll: Poll,
        questionIndex: Int,
        answerIndex: Int
    ) = withIO {
        val newAnswerList = mutableListOf<AnswerOption>()
        newAnswerList.addAll(poll.questions[questionIndex].answerOptions)

        newAnswerList.removeAt(answerIndex)

        val newQuestionList = mutableListOf<PollQuestion>()
        newQuestionList.addAll(poll.questions)

        newQuestionList[questionIndex] = newQuestionList[questionIndex].copy(
            text = newQuestionList[questionIndex].text,
            answerOptions = newAnswerList
        )

        poll.copy(questions = newQuestionList)
    }
}