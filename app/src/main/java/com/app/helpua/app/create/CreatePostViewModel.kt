package com.app.helpua.app.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.helpua.domain.models.Post
import com.app.helpua.domain.repositories.Repository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class CreatePostViewModel(
    private val repository: Repository.Firebase
) : ViewModel() {

    private var _action = MutableSharedFlow<Boolean>(replay = 1)
    val action = _action.asSharedFlow()

    var selectedType = 0

     fun createPost(
        userName: String,
        title: String,
        text: String,
        image: String,
        type: Int
    ) {
        viewModelScope.launch {
            repository.writePost(
                Post(author = userName, title = title, text = text, image = image, type = type)
            )
            _action.tryEmit(true)
        }
    }
}