package com.example.notesapp.ui.tasklist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.model.Task
import com.example.notesapp.data.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskListViewModel (
    private val repository: TaskRepository
): ViewModel(){
    var tasks by mutableStateOf<List<Task>>(emptyList())
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun loadTasks() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                tasks = repository.getTasks()
            } catch (e: Exception) {
                errorMessage = e.message ?: "Failed to load tasks"
            } finally {
                isLoading = false
            }
        }
    }

    fun deleteTask(id: String) {
        viewModelScope.launch {
            try {
                repository.deleteTask(id)
                loadTasks()
            } catch (e: Exception) {
                errorMessage = e.message ?: "Failed to delete task"
            }
        }
    }
}