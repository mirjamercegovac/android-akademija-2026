package com.example.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.data.repository.TaskRepository
import com.example.notesapp.ui.tasklist.TaskListViewModel

class TaskListViewModelFactory (
    private val repository: TaskRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TaskListViewModel::class.java) ->
                TaskListViewModel(repository) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}