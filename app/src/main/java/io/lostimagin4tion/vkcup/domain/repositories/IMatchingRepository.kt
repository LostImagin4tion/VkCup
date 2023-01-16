package io.lostimagin4tion.vkcup.domain.repositories

import io.lostimagin4tion.vkcup.domain.enitites.Matching

/**
 * [IMatchingRepository] - interface for description of [MatchingRepository]
 * repository that is used data manipulation of [NewMatchingScreen]
 *
 * @author Egor Danilov
 */
interface IMatchingRepository {

    suspend fun addQuestionAnswerPair(matching: Matching): Matching
    suspend fun deleteQuestionAnswerPair(matching: Matching, index: Int): Matching

    suspend fun changeQuestionText(
        matching: Matching,
        text: String,
        index: Int
    ): Matching

    suspend fun changeAnswerText(
        matching: Matching,
        text: String,
        index: Int
    ): Matching
}