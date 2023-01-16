package io.lostimagin4tion.vkcup.data.repositories

import io.lostimagin4tion.vkcup.domain.enitites.InteractiveElementData
import io.lostimagin4tion.vkcup.domain.enitites.PostData
import io.lostimagin4tion.vkcup.domain.enitites.Text
import io.lostimagin4tion.vkcup.domain.repositories.IWritePostRepository
import io.lostimagin4tion.vkcup.utils.withIO
import javax.inject.Inject

class WritePostRepository @Inject constructor(): IWritePostRepository {

    override suspend fun addInteractiveElement(
        postData: PostData,
        item: InteractiveElementData,
        index: Int
    ) = withIO {
        val newList = mutableListOf<InteractiveElementData>()
        newList.addAll(postData.elements)

        newList.add(index = index, element = item)

        postData.copy(elements = newList)
    }

    override suspend fun deleteInteractiveElement(postData: PostData, index: Int) = withIO {
        val newList = mutableListOf<InteractiveElementData>()
        newList.addAll(postData.elements)
        newList.removeAt(index)

        postData.copy(elements = newList)
    }

    override suspend fun updateTextElement(postData: PostData, text: String, index: Int) = withIO {
        if (postData.elements[index] is Text) {
            val newList = mutableListOf<InteractiveElementData>()
            newList.addAll(postData.elements)
            newList[index] = Text(text)

            postData.copy(elements = newList)
        } else {
            postData
        }
    }
}