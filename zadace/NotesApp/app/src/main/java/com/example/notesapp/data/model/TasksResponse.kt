package com.example.notesapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TasksResponse (
    val tasks: List<Task>
)