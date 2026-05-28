package com.example.notesapp

import android.content.Context
import androidx.room.Room
import com.example.notesapp.data.local.TaskieDatabase
import com.example.notesapp.data.repository.TaskRepository

class AppContainer(context: Context) {

    private val database by lazy {
        Room.databaseBuilder(
            context,
            TaskieDatabase::class.java,
            "taskie_database"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    val taskRepository by lazy {
        TaskRepository(
            taskDao = database.taskDao()
        )
    }
}