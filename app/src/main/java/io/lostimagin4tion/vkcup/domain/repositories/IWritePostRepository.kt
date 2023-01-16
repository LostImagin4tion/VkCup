package io.lostimagin4tion.vkcup.domain.repositories

import io.lostimagin4tion.vkcup.domain.enitites.InteractiveElementData
import io.lostimagin4tion.vkcup.domain.enitites.PostData

interface IWritePostRepository {

    suspend fun addInteractiveElement(
        postData: PostData,
        item: InteractiveElementData,
        index: Int
    ): PostData

    suspend fun deleteInteractiveElement(postData: PostData, index: Int): PostData

    suspend fun updateTextElement(postData: PostData, text: String, index: Int): PostData
}