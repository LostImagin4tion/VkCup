package io.lostimagin4tion.vkcup.domain.repositories

import io.lostimagin4tion.vkcup.domain.enitites.StarRating

/**
 * [IRatingRepository] - interface for description of [RatingRepository]
 * repository that is used data manipulation of [NewRatingScreen]
 *
 * @author Egor Danilov
 */
interface IRatingRepository {

    suspend fun addNewStar(rating: StarRating): StarRating
    suspend fun deleteStar(rating: StarRating): StarRating

    suspend fun changeClickedStar(rating: StarRating, index: Int): StarRating
}