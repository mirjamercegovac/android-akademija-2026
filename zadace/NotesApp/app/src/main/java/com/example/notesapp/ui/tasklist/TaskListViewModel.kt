package com.example.notesapp.ui.tasklist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.model.Task
import com.example.notesapp.data.repository.TaskRepository
import com.example.notesapp.di.AppLogger
import kotlinx.coroutines.launch

class TaskListViewModel (
    private val repository: TaskRepository,
    private val logger: AppLogger
): ViewModel(){
    var tasks by mutableStateOf<List<Task>>(emptyList())
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    init {
        observeTasks()
    }

    private fun observeTasks(){
        viewModelScope.launch {
            logger.debug("Starting Room Flow observation")
            repository.getTasksFlow().collect { localTasks ->
                tasks = localTasks
            }
        }
    }

    fun loadTasks() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            logger.debug("Loading tasks from repository")
            try {
                repository.refreshTasks()
            } catch (e: Exception) {
                errorMessage = e.message ?: "Failed to load tasks"
                logger.error("Failed to load tasks", e)
            } finally {
                isLoading = false
            }
        }
    }

    fun deleteTask(id: String) {
        viewModelScope.launch {
            logger.debug("Deleting task id=$id")
            try {
                repository.deleteTask(id)
            } catch (e: Exception) {
                errorMessage = e.message ?: "Failed to delete task"
                logger.error("Failed to delete task", e)
            }
        }
    }
}