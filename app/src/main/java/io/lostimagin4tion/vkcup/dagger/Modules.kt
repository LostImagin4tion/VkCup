package io.lostimagin4tion.vkcup.dagger

import dagger.Binds
import dagger.Module
import io.lostimagin4tion.vkcup.data.repositories.DraggingIntoGapsRepository
import io.lostimagin4tion.vkcup.data.repositories.FillingGapsRepository
import io.lostimagin4tion.vkcup.data.repositories.MatchingRepository
import io.lostimagin4tion.vkcup.data.repositories.PollRepository
import io.lostimagin4tion.vkcup.data.repositories.PostRepository
import io.lostimagin4tion.vkcup.data.repositories.RatingRepository
import io.lostimagin4tion.vkcup.data.repositories.WatchPostRepository
import io.lostimagin4tion.vkcup.data.repositories.WritePostRepository
import io.lostimagin4tion.vkcup.domain.repositories.IDraggingIntoGapsRepository
import io.lostimagin4tion.vkcup.domain.repositories.IFillingGapsRepository
import io.lostimagin4tion.vkcup.domain.repositories.IMatchingRepository
import io.lostimagin4tion.vkcup.domain.repositories.IPollRepository
import io.lostimagin4tion.vkcup.domain.repositories.IPostRepository
import io.lostimagin4tion.vkcup.domain.repositories.IRatingRepository
import io.lostimagin4tion.vkcup.domain.repositories.IWatchPostRepository
import io.lostimagin4tion.vkcup.domain.repositories.IWritePostRepository
import javax.inject.Singleton

/**
 * [RepositoriesModule] - abstract class for binding all repositories
 * and then providing it to AppComponent
 *
 * @author Egor Danilov
 */
@Module
abstract class RepositoriesModule {

    @Singleton
    @Binds
    abstract fun bindIPostRepository(impl: PostRepository): IPostRepository

    @Singleton
    @Binds
    abstract fun bindIWritePostRepository(impl: WritePostRepository): IWritePostRepository

    @Singleton
    @Binds
    abstract fun bindIPollRepository(impl: PollRepository): IPollRepository

    @Singleton
    @Binds
    abstract fun bindIRatingRepository(impl: RatingRepository): IRatingRepository

    @Singleton
    @Binds
    abstract fun bindIMatchingRepository(impl: MatchingRepository): IMatchingRepository

    @Singleton
    @Binds
    abstract fun bindIDraggingIntoGapsRepository(impl: DraggingIntoGapsRepository): IDraggingIntoGapsRepository

    @Singleton
    @Binds
    abstract fun bindIFillingGapsRepository(impl: FillingGapsRepository): IFillingGapsRepository

    @Singleton
    @Binds
    abstract fun bindIWatchPostRepository(impl: WatchPostRepository): IWatchPostRepository
}