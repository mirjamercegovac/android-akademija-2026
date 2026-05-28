package com.example.notesapp.ui.edittask

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.repository.TaskRepository
import kotlinx.coroutines.launch

class EditTaskViewModel (
    private val repository: TaskRepository
): ViewModel() {
    var title by mutableStateOf("")
    var body by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)

    fun loadTask(id: String) {
        viewModelScope.launch {
            try {
                val task = repository.getTaskById(id)
                if (task != null) {
                    title = task.title
                    body = task.body
                } else {
                    errorMessage = "Task not found"
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Failed to load task"
            }
        }
    }

    fun createTask(onDone: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.createTask(title, body)
                onDone()
            } catch (e: Exception) {
                errorMessage = e.message ?: "Failed to create task"
            }
        }
    }

    fun updateTask(id: String, onDone: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.updateTask(id, title, body)
                onDone()
            } catch (e: Exception) {
                errorMessage = e.message ?: "Failed to update task"
            }
        }
    }
}