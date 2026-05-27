package com.example.notesapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTaskRequest (
    val title: String,
    val body: String
)