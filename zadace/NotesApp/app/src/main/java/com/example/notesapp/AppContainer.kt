package com.example.notesapp

import com.example.notesapp.data.repository.TaskRepository

class AppContainer {
    val taskRepository by lazy {
        TaskRepository()
    }
}