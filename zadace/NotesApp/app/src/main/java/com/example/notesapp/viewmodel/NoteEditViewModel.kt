package com.example.notesapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.notesapp.data.NoteRepository

class NoteEditViewModel (
    private val repository: NoteRepository
) : ViewModel(){
    var title by mutableStateOf("")
    var content by mutableStateOf("")
    var createdAt by mutableStateOf("")
    private var currentNoteId: Int? = null

    fun loadNote(noteId: Int) {
        if (noteId == -1) {
            currentNoteId = null
            title = ""
            content = ""
            createdAt = ""
            return
        }

        val note = repository.getNoteById(noteId)
        currentNoteId = note?.id
        title = note?.title.orEmpty()
        content = note?.content.orEmpty()
        createdAt = note?.createdAt.orEmpty()
    }

    fun saveNote() {
        if (currentNoteId == null) {
            repository.addNote(
                title = title,
                content = content,
                createdAt = if (createdAt.isBlank()) "26.04.2026." else createdAt
            )
        } else {
            repository.updateNote(
                id = currentNoteId!!,
                title = title,
                content = content
            )
        }
    }
}