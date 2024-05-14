package com.app.helpua.data.datasources.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.app.helpua.data.datasources.DataSource
import com.app.helpua.data.datasources.utils.toUser
import com.app.helpua.domain.models.User
import com.app.helpua.domain.utils.DEFAULT_EXCEPTION
import com.app.helpua.domain.utils.Results

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthDataSource : DataSource.Auth {

    private var auth: FirebaseAuth = Firebase.auth

    override suspend fun createUser(
        password: String,
        email: String,
        displayName: String
    ): Results<User> = withContext(Dispatchers.IO) {

        return@withContext try {

            val authResult =
                auth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user

            auth.currentUser?.updateProfile(
                UserProfileChangeRequest.Builder().setDisplayName(displayName).build()
            )

            if (user != null) {
                Log.d("User:", user.toUser().toString())
                Results.Success(user.toUser())
            } else {
                Results.Error(Exception("User is Null"))
            }
        } catch (e: Exception) {
            Results.Error(e)
        }
    }

    override suspend fun signIn(
        password: String,
        email: String
    ): Results<User> {

        return try {
            val authResult = withContext(Dispatchers.IO) {
                auth.signInWithEmailAndPassword(email, password).await()
            }.user

            when (authResult) {
                null -> Results.Error(Exception("User is null"))
                else -> Results.Success(authResult.toUser())
            }

        } catch (e: Exception) {
            Results.Error(e)
        }
    }

    override suspend fun getCurrentUser(): Results<User> = withContext(Dispatchers.IO) {

        val currentUser = auth.currentUser

        return@withContext if (currentUser != null) {
            Results.Success(currentUser.toUser())
        } else {
            Results.Error(Exception(DEFAULT_EXCEPTION))
        }

    }

    override suspend fun signOut(): Results<User?> = withContext(Dispatchers.IO) {
        auth.signOut()

        return@withContext if (auth.currentUser == null) {
            Results.Success(null)
        } else {
            Results.Error(Exception("Sign Out Error"))
        }
    }

}