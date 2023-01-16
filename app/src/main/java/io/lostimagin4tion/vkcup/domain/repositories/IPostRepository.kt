package io.lostimagin4tion.vkcup.domain.repositories

import io.lostimagin4tion.vkcup.domain.enitites.PostData
import io.lostimagin4tion.vkcup.domain.enitites.Posts

interface IPostRepository {

    suspend fun addPost(postItems: Posts, newPost: PostData): Posts
    suspend fun deletePost(postItems: Posts, index: Int): Posts
}