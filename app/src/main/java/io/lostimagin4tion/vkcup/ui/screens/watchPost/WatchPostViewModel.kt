package io.lostimagin4tion.vkcup.ui.screens.watchPost

import androidx.lifecycle.ViewModel
import io.lostimagin4tion.vkcup.VkCupApplication
import io.lostimagin4tion.vkcup.dagger.AppComponent
import io.lostimagin4tion.vkcup.domain.enitites.QuizWithGaps
import io.lostimagin4tion.vkcup.domain.repositories.IWatchPostRepository
import javax.inject.Inject

class WatchPostViewModel(
    appComponent: AppComponent = VkCupApplication.appComponent
): ViewModel() {

    @Inject lateinit var watchPostRepository: IWatchPostRepository

    init {
        appComponent.inject(this)
    }

    fun convertQuizWithGapsData(quizWithGaps: QuizWithGaps) =
        watchPostRepository.convertQuizWithGaps(quizWithGaps)
}