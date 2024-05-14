package com.app.helpua.data.datasources.utils

import android.content.SharedPreferences

class DataRepository(private val sharedPreferences: SharedPreferences) {


    fun saveUserName(name: String) {
        sharedPreferences.edit().putString("NAME", name).apply()
    }

    fun getUserName() = sharedPreferences.getString("NAME", "Name")
}