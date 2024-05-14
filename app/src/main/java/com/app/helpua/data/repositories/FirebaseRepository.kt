package com.app.helpua.data.repositories

import com.app.helpua.data.datasources.DataSource
import com.app.helpua.domain.models.Post
import com.app.helpua.domain.models.User
import com.app.helpua.domain.repositories.Repository
import com.app.helpua.domain.utils.Results
import kotlinx.coroutines.flow.Flow

class FirebaseRepository(
    private val authDataSource: DataSource.Auth,
    private val dataBaseDataSource: DataSource.DataBase
) : Repository.Firebase {

    override suspend fun getCurrentUser(): Results<User> = authDataSource.getCurrentUser()

    override suspend fun signIn(password: String, email: String): Results<User> {
        return authDataSource.signIn(password = password, email = email)
    }

    override suspend fun createUser(
        password: String,
        email: String,
        displayName: String
    ): Results<User> {
        return authDataSource.createUser(
            password = password,
            email = email,
            displayName = displayName
        )
    }

    override suspend fun signOut(): Results<User?> {
        return authDataSource.signOut()
    }

    override suspend fun writePost(post: Post): Unit = dataBaseDataSource.writePost(post = post)

    override suspend fun readPosts(): Results<List<Post>> = dataBaseDataSource.readPosts()

    override suspend fun subscribeToPosts(): Flow<List<Post>> {
        return dataBaseDataSource.subscribeToPosts()
    }
}