package com.app.helpua.domain.models

data class User(
    val uid: String,
    var displayName: String?,
    val email: String?,
    val photoUrl: String?
)