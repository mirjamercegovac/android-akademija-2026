package com.example.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.data.repository.TaskRepository
import com.example.notesapp.ui.edittask.EditTaskViewModel

class EditTaskViewModelFactory (
    private val repository: TaskRepository
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(EditTaskViewModel::class.java) ->
                EditTaskViewModel(repository) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}