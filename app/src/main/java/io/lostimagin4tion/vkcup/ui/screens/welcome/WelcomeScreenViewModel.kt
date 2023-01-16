package io.lostimagin4tion.vkcup.ui.screens.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.lostimagin4tion.vkcup.VkCupApplication
import io.lostimagin4tion.vkcup.dagger.AppComponent
import io.lostimagin4tion.vkcup.domain.enitites.PostData
import io.lostimagin4tion.vkcup.domain.enitites.Posts
import io.lostimagin4tion.vkcup.domain.repositories.IPostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class WelcomeScreenViewModel(
    appComponent: AppComponent = VkCupApplication.appComponent
): ViewModel() {

    @Inject lateinit var postRepository: IPostRepository

    init {
        appComponent.inject(this)
    }

    private val _postsData = MutableStateFlow(Posts())
    val postsData: StateFlow<Posts> = _postsData

    var clickedPostIndex: Int = 0

    fun addPost(post: PostData) = viewModelScope.launch {
        _postsData.value = postRepository.addPost(
            postItems = _postsData.value,
            newPost = post
        )
    }

    fun deletePost(index: Int) = viewModelScope.launch {
        _postsData.value = postRepository.deletePost(
            postItems = _postsData.value,
            index = index
        )
    }
}