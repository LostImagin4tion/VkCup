package io.lostimagin4tion.vkcup.ui.screens.newFillingGaps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.lostimagin4tion.vkcup.VkCupApplication
import io.lostimagin4tion.vkcup.dagger.AppComponent
import io.lostimagin4tion.vkcup.domain.enitites.GapsInteractionMethod
import io.lostimagin4tion.vkcup.domain.enitites.QuizWithGaps
import io.lostimagin4tion.vkcup.domain.repositories.IFillingGapsRepository
import io.lostimagin4tion.vkcup.ui.screens.newDraggingIntoGaps.NewDraggingIntoGapsScreen
import io.lostimagin4tion.vkcup.ui.screens.newDraggingIntoGaps.NewDraggingIntoGapsViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [NewFillingGapsViewModel] - viewModel for [NewFillingGapsScreen]
 * Performs all manipulations with data to update UI
 *
 * @author Egor Danilov
 */
class NewFillingGapsViewModel(
    appComponent: AppComponent = VkCupApplication.appComponent
): ViewModel() {

    @Inject
    lateinit var fillingGapsRepository: IFillingGapsRepository

    init {
        appComponent.inject(this)
    }

    private val _quizData = MutableStateFlow(
        QuizWithGaps(interactionMethod = GapsInteractionMethod.Filling)
    )
    val quizData: StateFlow<QuizWithGaps> = _quizData

    fun updateQuizText(text: String) = viewModelScope.launch {
        _quizData.value = fillingGapsRepository.updateQuizText(
            quizData = _quizData.value,
            text = text
        )
    }

    fun addNewGap(text: String) = viewModelScope.launch {
        if (text.isNotBlank()) {
            _quizData.value = fillingGapsRepository.addNewGap(
                quizData = _quizData.value,
                text = text.trim()
            )
        }
    }

    fun deleteGap(index: Int) = viewModelScope.launch {
        _quizData.value = fillingGapsRepository.deleteGap(
            quizData = _quizData.value,
            index = index
        )
    }
}