package io.lostimagin4tion.vkcup.ui.screens.writePost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.lostimagin4tion.vkcup.VkCupApplication
import io.lostimagin4tion.vkcup.dagger.AppComponent
import io.lostimagin4tion.vkcup.domain.enitites.InteractiveElementData
import io.lostimagin4tion.vkcup.domain.enitites.PostData
import io.lostimagin4tion.vkcup.domain.enitites.Text
import io.lostimagin4tion.vkcup.domain.repositories.IWritePostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class WritePostViewModel(
    appComponent: AppComponent = VkCupApplication.appComponent
): ViewModel() {

    @Inject lateinit var writePostRepository: IWritePostRepository

    init {
        appComponent.inject(this)
    }

    private val _writePostData = MutableStateFlow(PostData())
    val writePostData: StateFlow<PostData> = _writePostData

    var rememberedIndex = 0

    fun addInteractiveElement(
        element: InteractiveElementData,
        index: Int
    ) = viewModelScope.launch {
        _writePostData.value = writePostRepository.addInteractiveElement(
            postData = _writePostData.value,
            item = element,
            index = index
        )
    }

    fun deleteInteractiveElement(index: Int) = viewModelScope.launch {
        println(_writePostData.value)
        _writePostData.value = writePostRepository.deleteInteractiveElement(
            postData = _writePostData.value,
            index = index
        )
    }

    fun updateTextElement(text: String, index: Int) = viewModelScope.launch {
        _writePostData.value = writePostRepository.updateTextElement(
            postData = _writePostData.value,
            text = text,
            index = index
        )
    }
}