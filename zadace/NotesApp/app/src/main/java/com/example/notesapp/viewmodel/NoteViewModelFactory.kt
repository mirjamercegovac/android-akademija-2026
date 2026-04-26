package com.example.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.data.NoteRepository

class NoteViewModelFactory (
    private val repository: NoteRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(NotesListViewModel::class.java) ->
                NotesListViewModel(repository) as T

            modelClass.isAssignableFrom(NoteEditViewModel::class.java) ->
                NoteEditViewModel(repository) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}