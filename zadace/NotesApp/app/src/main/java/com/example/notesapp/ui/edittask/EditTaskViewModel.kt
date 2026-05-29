package com.example.notesapp.ui.edittask

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.repository.TaskRepository
import com.example.notesapp.di.AppLogger
import kotlinx.coroutines.launch

class EditTaskViewModel (
    private val repository: TaskRepository,
    private val logger: AppLogger
): ViewModel() {
    var title by mutableStateOf("")
    var body by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)

    fun loadTask(id: String) {
        viewModelScope.launch {
            logger.debug("Loading task id=$id")
            try {
                val task = repository.getTaskById(id)
                if (task != null) {
                    title = task.title
                    body = task.body
                    logger.debug("Task loaded successfully")
                } else {
                    errorMessage = "Task not found"
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Failed to load task"
                logger.error("Failed to load task", e)
            }
        }
    }

    fun createTask(onDone: () -> Unit) {
        viewModelScope.launch {
            logger.debug("Creating task")
            try {
                repository.createTask(title, body)
                onDone()
            } catch (e: Exception) {
                errorMessage = e.message ?: "Failed to create task"
                logger.error("Failed to create task", e)
            }
        }
    }

    fun updateTask(id: String, onDone: () -> Unit) {
        viewModelScope.launch {
            logger.debug("Updating task id=$id")
            try {
                repository.updateTask(id, title, body)
                onDone()
            } catch (e: Exception) {
                errorMessage = e.message ?: "Failed to update task"
                logger.error("Failed to update task", e)
            }
        }
    }
}