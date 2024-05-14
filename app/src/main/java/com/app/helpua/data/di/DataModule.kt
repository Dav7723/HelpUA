package com.app.helpua.data.di

import android.content.Context
import android.content.SharedPreferences
import com.app.helpua.data.datasources.DataSource
import com.app.helpua.data.datasources.firebase.AuthDataSource
import com.app.helpua.data.datasources.firebase.DataBaseDataSource
import com.app.helpua.data.datasources.utils.DataRepository
import com.app.helpua.data.repositories.FirebaseRepository
import com.app.helpua.domain.repositories.Repository
import org.koin.dsl.module

val dataModule = module {
    single<DataSource.Auth> { AuthDataSource(get()) }
    single<DataSource.DataBase> { DataBaseDataSource()}
    single<Repository.Firebase> {FirebaseRepository(get(), get())}


    single { DataRepository(get())}
    single<SharedPreferences> {
        get<Context>().getSharedPreferences("DATA_BASE", Context.MODE_PRIVATE)
    }
}