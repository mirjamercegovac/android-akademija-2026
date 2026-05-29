package com.example.notesapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity (
    @PrimaryKey
    val id: String,
    val title: String,
    val body: String,
    val category: String = "Other",
    val createdAt: String = "",
    val isFavorite: Boolean = false,
    val isSynced: Boolean = true
)