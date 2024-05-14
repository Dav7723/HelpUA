package com.app.helpua.app.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.helpua.domain.models.User
import com.app.helpua.domain.repositories.Repository
import com.app.helpua.domain.utils.Results
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: Repository.Firebase
) : ViewModel() {

    private var _signInFlow = MutableSharedFlow<Results<User>>(replay = 1)
    val signInFlow = _signInFlow.asSharedFlow()

    private var _signUpFlow = MutableSharedFlow<Results<User>>(replay = 1)
    val signUpFlow = _signUpFlow.asSharedFlow()

     fun signIn(password: String, email: String) {
        viewModelScope.launch {
            _signInFlow.tryEmit(repository.signIn(password = password, email = email))
        }
    }
     fun signUp(password: String, email: String, name: String) {
        viewModelScope.launch {
            _signUpFlow.tryEmit(repository.createUser(password = password, displayName = name, email = email))
        }
    }

}