package com.app.helpua.app.di


import com.app.helpua.app.create.CreatePostViewModel
import com.app.helpua.app.home.HomeViewModel
import com.app.helpua.app.profile.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    viewModelOf(::AuthViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::CreatePostViewModel)

}