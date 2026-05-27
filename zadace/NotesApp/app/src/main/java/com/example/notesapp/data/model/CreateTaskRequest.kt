package com.example.notesapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateTaskRequest (
    val title: String,
    val body: String
)