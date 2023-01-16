package io.lostimagin4tion.vkcup.ui.screens.newMatching

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.lostimagin4tion.vkcup.VkCupApplication
import io.lostimagin4tion.vkcup.dagger.AppComponent
import io.lostimagin4tion.vkcup.domain.enitites.Matching
import io.lostimagin4tion.vkcup.domain.enitites.QuestionAnswerPair
import io.lostimagin4tion.vkcup.domain.repositories.IMatchingRepository
import io.lostimagin4tion.vkcup.ui.screens.newDraggingIntoGaps.NewDraggingIntoGapsScreen
import io.lostimagin4tion.vkcup.ui.screens.newDraggingIntoGaps.NewDraggingIntoGapsViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [NewMatchingViewModel] - viewModel for [NewMatchingScreen]
 * Performs all manipulations with data to update UI
 *
 * @author Egor Danilov
 */
class NewMatchingViewModel(
    appComponent: AppComponent = VkCupApplication.appComponent
): ViewModel() {

    @Inject lateinit var matchingRepository: IMatchingRepository

    init {
        appComponent.inject(this)
    }

    private val _matchingData = MutableStateFlow(defaultMatching())
    val matchingData: StateFlow<Matching> = _matchingData

    private fun defaultMatching() = Matching(
        items = listOf(QuestionAnswerPair())
    )

    fun addQuestionAnswerPair() = viewModelScope.launch {
        _matchingData.value = matchingRepository.addQuestionAnswerPair(_matchingData.value)
    }

    fun deleteQuestionAnswerPair(index: Int) = viewModelScope.launch {
        _matchingData.value = matchingRepository.deleteQuestionAnswerPair(
            matching = _matchingData.value,
            index = index
        )
    }

    fun changeQuestionText(text: String, index: Int) = viewModelScope.launch {
        _matchingData.value = matchingRepository.changeQuestionText(
            matching = _matchingData.value,
            text = text,
            index = index
        )
    }

    fun changeAnswerText(text: String, index: Int) = viewModelScope.launch {
        _matchingData.value = matchingRepository.changeAnswerText(
            matching = _matchingData.value,
            text = text,
            index = index
        )
    }
}