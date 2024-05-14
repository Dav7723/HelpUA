package com.app.helpua.domain.repositories

import com.app.helpua.domain.models.Post
import com.app.helpua.domain.models.User
import com.app.helpua.domain.utils.Results
import kotlinx.coroutines.flow.Flow

interface Repository {

    interface Firebase {
        suspend fun getCurrentUser(): Results<User>

        suspend fun signIn(password: String, email: String): Results<User>

        suspend fun createUser(password: String, email: String, displayName: String): Results<User>
        suspend fun signOut(): Results<User?>
        suspend fun writePost(post: Post)

        suspend fun readPosts(): Results<List<Post>>

        suspend fun subscribeToPosts(): Flow<List<Post>>

    }
}