package com.app.helpua.app.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.helpua.data.datasources.utils.DataRepository
import com.app.helpua.domain.models.Post
import com.app.helpua.domain.models.User
import com.app.helpua.domain.repositories.Repository
import com.app.helpua.domain.utils.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: Repository.Firebase,
    private val dataRepository: DataRepository
) : ViewModel() {

    private var _posts = MutableSharedFlow<List<Post>>(replay = 1)
    val posts = _posts.asSharedFlow()
    private var _user = MutableSharedFlow<Results<User?>>(replay = 1)
    val user = _user.asSharedFlow()
    fun getIsAuth() {
        viewModelScope.launch(Dispatchers.IO) {
            _user.tryEmit(repository.getCurrentUser())
        }
    }

    fun getUserName(): String? = dataRepository.getUserName()

    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            _user.tryEmit(repository.signOut())
        }
    }

    fun subcribeToPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.subscribeToPosts().collect {
                _posts.tryEmit(it)
            }
        }
    }

}