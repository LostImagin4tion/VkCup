package io.lostimagin4tion.vkcup.data.repositories

import io.lostimagin4tion.vkcup.domain.enitites.PostData
import io.lostimagin4tion.vkcup.domain.enitites.Posts
import io.lostimagin4tion.vkcup.domain.repositories.IPostRepository
import io.lostimagin4tion.vkcup.utils.withIO
import javax.inject.Inject

class PostRepository @Inject constructor(): IPostRepository {

    override suspend fun addPost(postItems: Posts, newPost: PostData) = withIO {
        postItems.copy(
            posts = postItems.posts + listOf(newPost)
        )
    }

    override suspend fun deletePost(postItems: Posts, index: Int) = withIO {
        val newList = mutableListOf<PostData>()
        newList.addAll(postItems.posts)
        newList.removeAt(index)

        postItems.copy(posts = newList)
    }
}