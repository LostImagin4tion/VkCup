package io.lostimagin4tion.vkcup.dagger

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import io.lostimagin4tion.vkcup.ui.screens.newDraggingIntoGaps.NewDraggingIntoGapsViewModel
import io.lostimagin4tion.vkcup.ui.screens.newFillingGaps.NewFillingGapsViewModel
import io.lostimagin4tion.vkcup.ui.screens.newMatching.NewMatchingViewModel
import io.lostimagin4tion.vkcup.ui.screens.newPoll.NewPollViewModel
import io.lostimagin4tion.vkcup.ui.screens.newRating.NewRatingViewModel
import io.lostimagin4tion.vkcup.ui.screens.watchPost.WatchPostViewModel
import io.lostimagin4tion.vkcup.ui.screens.welcome.WelcomeScreenViewModel
import io.lostimagin4tion.vkcup.ui.screens.writePost.WritePostViewModel
import javax.inject.Singleton

/**
 * [AppComponent] - **Dagger** component, which provides all necessary dependencies to DI graph
 *
 * @author Egor Danilov
 */
@Singleton
@Component(modules = [RepositoriesModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }

    fun inject(welcomeScreenViewModel: WelcomeScreenViewModel)
    fun inject(writePostViewModel: WritePostViewModel)
    fun inject(newPollViewModel: NewPollViewModel)
    fun inject(newRatingViewModel: NewRatingViewModel)
    fun inject(newMatchingViewModel: NewMatchingViewModel)
    fun inject(newDraggingIntoGapsViewModel: NewDraggingIntoGapsViewModel)
    fun inject(newFillingGapsViewModel: NewFillingGapsViewModel)
    fun inject(watchPostViewModel: WatchPostViewModel)
}