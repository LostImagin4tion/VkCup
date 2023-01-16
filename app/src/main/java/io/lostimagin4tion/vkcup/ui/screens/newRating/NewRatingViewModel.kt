package io.lostimagin4tion.vkcup.ui.screens.newRating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.lostimagin4tion.vkcup.VkCupApplication
import io.lostimagin4tion.vkcup.dagger.AppComponent
import io.lostimagin4tion.vkcup.domain.enitites.StarRating
import io.lostimagin4tion.vkcup.domain.repositories.IRatingRepository
import io.lostimagin4tion.vkcup.ui.screens.newDraggingIntoGaps.NewDraggingIntoGapsScreen
import io.lostimagin4tion.vkcup.ui.screens.newDraggingIntoGaps.NewDraggingIntoGapsViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [NewRatingViewModel] - viewModel for [NewRatingScreen]
 * Performs all manipulations with data to update UI
 *
 * @author Egor Danilov
 */
class NewRatingViewModel(
    appComponent: AppComponent = VkCupApplication.appComponent
): ViewModel() {

    @Inject lateinit var ratingRepository: IRatingRepository

    init {
        appComponent.inject(this)
    }

    private val _ratingData = MutableStateFlow(StarRating())
    val ratingData: StateFlow<StarRating> = _ratingData

    fun addNewStar() = viewModelScope.launch {
        _ratingData.value = ratingRepository.addNewStar(_ratingData.value)
    }

    fun deleteStar() = viewModelScope.launch {
        _ratingData.value = ratingRepository.deleteStar(_ratingData.value)
    }

    fun changeClickedStar(index: Int) = viewModelScope.launch {
        _ratingData.value = ratingRepository.changeClickedStar(
            rating = _ratingData.value,
            index = index
        )
    }
}