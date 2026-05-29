package com.example.notesapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Task (
    val id: String,
    val title: String,
    val body: String,
    val category: String = "Other",
    val createdAt: String = "",
    val isFavorite: Boolean = false
)