package com.example.notesapp.data.model

import kotlinx.serialization.Serializable

//ZADACA 5 - response login vraca token
@Serializable
data class LoginResponse (
    val token: String
)