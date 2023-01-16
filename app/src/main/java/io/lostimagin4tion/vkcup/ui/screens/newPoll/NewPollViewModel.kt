package io.lostimagin4tion.vkcup.ui.screens.newPoll

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.lostimagin4tion.vkcup.VkCupApplication
import io.lostimagin4tion.vkcup.dagger.AppComponent
import io.lostimagin4tion.vkcup.domain.enitites.AnswerOption
import io.lostimagin4tion.vkcup.domain.enitites.Poll
import io.lostimagin4tion.vkcup.domain.enitites.PollQuestion
import io.lostimagin4tion.vkcup.domain.repositories.IPollRepository
import io.lostimagin4tion.vkcup.ui.screens.newDraggingIntoGaps.NewDraggingIntoGapsScreen
import io.lostimagin4tion.vkcup.ui.screens.newDraggingIntoGaps.NewDraggingIntoGapsViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [NewPollViewModel] - viewModel for [NewPollScreen]
 * Performs all manipulations with data to update UI
 *
 * @author Egor Danilov
 */
class NewPollViewModel(
    appComponent: AppComponent = VkCupApplication.appComponent
) : ViewModel() {

    @Inject lateinit var pollRepository: IPollRepository

    init {
        appComponent.inject(this)
    }

    private var _pollData = MutableStateFlow(defaultPoll())
    val pollData: StateFlow<Poll> = _pollData

    private fun defaultPoll() = Poll(
        theme = "",
        questions = mutableListOf(
            PollQuestion(
                text = "",
                answerOptions = mutableListOf(
                    AnswerOption(text = "", isCorrect = false),
                    AnswerOption(text = "", isCorrect = false)
                )
            )
        )
    )

    fun addNewQuestion(text: String) = viewModelScope.launch {
        _pollData.value = pollRepository.addNewQuestion(poll = _pollData.value, text = text)
    }

    fun addNewAnswerOption(
        questionIndex: Int,
        text: String,
        isCorrect: Boolean
    ) = viewModelScope.launch {
        _pollData.value = pollRepository.addNewAnswerOption(
            poll = _pollData.value,
            questionIndex = questionIndex,
            text = text,
            isCorrect = isCorrect
        )
    }

    fun changePollTheme(text: String) = viewModelScope.launch {
        _pollData.value = pollRepository.changePollTheme(poll = _pollData.value, text = text)
    }

    fun changeQuestionText(text: String, questionIndex: Int) = viewModelScope.launch {
        _pollData.value = pollRepository.changeQuestionText(
            poll = _pollData.value,
            text = text,
            questionIndex = questionIndex
        )
    }

    fun changeAnswerOptionText(
        text: String,
        questionIndex: Int,
        answerIndex: Int
    ) = viewModelScope.launch {
        _pollData.value = pollRepository.changeAnswerOptionText(
            poll = _pollData.value,
            text = text,
            questionIndex = questionIndex,
            answerIndex = answerIndex
        )
    }

    fun updateCorrectAnswer(
        questionIndex: Int,
        answerIndex: Int,
        isCorrect: Boolean
    ) = viewModelScope.launch {
        _pollData.value = pollRepository.updateCorrectAnswer(
            poll = _pollData.value,
            questionIndex = questionIndex,
            answerIndex = answerIndex,
            isCorrect = isCorrect
        )
    }

    fun deleteQuestion(questionIndex: Int) = viewModelScope.launch {
        _pollData.value = pollRepository.deleteQuestion(
            poll = _pollData.value,
            questionIndex = questionIndex
        )
    }

    fun deleteAnswer(questionIndex: Int, answerIndex: Int) = viewModelScope.launch {
        _pollData.value = pollRepository.deleteAnswerOption(
            poll = _pollData.value,
            questionIndex = questionIndex,
            answerIndex = answerIndex
        )
    }
}