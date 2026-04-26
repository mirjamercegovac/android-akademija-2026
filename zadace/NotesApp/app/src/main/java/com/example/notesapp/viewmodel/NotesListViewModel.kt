package com.example.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.notesapp.model.Note
import com.example.notesapp.data.NoteRepository

class NotesListViewModel (
    private val repository: NoteRepository
) : ViewModel(){
    fun getNotes(): List<Note> = repository.getAllNotes()
}