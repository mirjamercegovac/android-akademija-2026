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

    var allTasks by mutableStateOf<List<Task>>(emptyList())
    var searchQuery by mutableStateOf("")

    init {
        observeTasks()
    }

    private fun observeTasks() {
        viewModelScope.launch {
            logger.debug("Starting Room Flow observation")
            repository.getTasksFlow().collect { localTasks ->
                allTasks = localTasks.sortedByDescending { it.isFavorite }
                filterTasks()
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        searchQuery = query
        filterTasks()
    }

    private fun filterTasks() {
        val query = searchQuery.trim().lowercase()

        tasks = if (query.isBlank()) {
            allTasks
        } else {
            allTasks.filter { task ->
                task.title.lowercase().contains(query) ||
                        task.body.lowercase().contains(query) ||
                        task.category.lowercase().contains(query)
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

    fun toggleFavorite(id: String, isFavorite: Boolean) {
        viewModelScope.launch {
            try {
                repository.toggleFavorite(id, isFavorite)
            } catch (e: Exception) {
                errorMessage = e.message ?: "Failed to update favorite"
                logger.error("Failed to update favorite", e)
            }
        }
    }
}