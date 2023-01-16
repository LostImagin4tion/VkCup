package io.lostimagin4tion.vkcup.data.repositories

import io.lostimagin4tion.vkcup.domain.enitites.StarRating
import io.lostimagin4tion.vkcup.domain.repositories.IDraggingIntoGapsRepository
import io.lostimagin4tion.vkcup.domain.repositories.IRatingRepository
import io.lostimagin4tion.vkcup.utils.withIO
import javax.inject.Inject

/**
 * [RatingRepository] - repository that is used data manipulation of [NewRatingScreen]
 *
 * @see [IRatingRepository]
 *
 * @author Egor Danilov
 */
class RatingRepository @Inject constructor(): IRatingRepository {

    override suspend fun addNewStar(rating: StarRating) = withIO {
        rating.copy(size = rating.size + 1)
    }

    override suspend fun deleteStar(rating: StarRating) = withIO {
        rating.copy(
            size = if (rating.size - 1 >= 0) rating.size - 1 else 0,
            clickedStarIndex = if (rating.clickedStarIndex == rating.size - 1) {
                rating.size - 2
            } else {
                rating.clickedStarIndex
            }
        )
    }

    override suspend fun changeClickedStar(rating: StarRating, index: Int) = withIO {
        rating.copy(clickedStarIndex = index)
    }
}