package com.app.helpua.data.datasources.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.app.helpua.data.datasources.DataSource
import com.app.helpua.domain.models.Post
import com.app.helpua.domain.utils.Results
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.util.UUID


class DataBaseDataSource : DataSource.DataBase {

    private val database = FirebaseDatabase.getInstance()
    private val reference = database.getReference(PARENT_PATH_STRING)

    override suspend fun writePost(post: Post): Unit = withContext(Dispatchers.IO) {

        try {
            val currentUser = FirebaseAuth.getInstance().currentUser

            if (currentUser != null) {
                reference.child(CHILD_PATH_STRING + UUID.randomUUID()).setValue(post).await()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue()
                Log.d("TAG", "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }


    override suspend fun readPosts(): Results<List<Post>> = withContext(Dispatchers.IO) {
        val posts = mutableListOf<Post>()

        try {
            val snapshot = reference.get().await()

            for (childSnapshot in snapshot.children) {

                val user = childSnapshot.getValue(Post::class.java)

                if (user != null) {
                    posts.add(user)
                }
            }

        } catch (e: Exception) {

            return@withContext Results.Error(e)
        }

        return@withContext Results.Success(posts)
    }

    override suspend fun subscribeToPosts(): Flow<List<Post>> = callbackFlow {

            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val posts = mutableListOf<Post>()
                    for (childSnapshot in snapshot.children) {

                        val user = childSnapshot.getValue(Post::class.java)

                        if (user != null) {
                            posts.add(user)
                        }
                    }
                    this@callbackFlow.trySend(posts).isSuccess
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            }

            reference.addValueEventListener(listener)

        awaitClose { reference.removeEventListener(listener) }

    }.distinctUntilChanged().flowOn(Dispatchers.IO)


    companion object {
        const val PARENT_PATH_STRING = "posts"
        const val CHILD_PATH_STRING = "post: "
    }
}