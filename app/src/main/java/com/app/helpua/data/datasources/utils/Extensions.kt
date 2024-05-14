package com.app.helpua.data.datasources.utils

import com.google.firebase.auth.FirebaseUser
import com.app.helpua.domain.models.User

fun FirebaseUser.toUser(): User =
    User(
        uid = this.uid,
        displayName = this.displayName.toString(),
        email = this.email,
        photoUrl = this.photoUrl.toString()
    )