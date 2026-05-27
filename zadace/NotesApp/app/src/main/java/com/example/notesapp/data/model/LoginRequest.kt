package com.example.notesapp.data.model

import kotlinx.serialization.Serializable

// ZADACA 5 - request za login
@Serializable
data class LoginRequest (
    val username: String,
    val password: String
)