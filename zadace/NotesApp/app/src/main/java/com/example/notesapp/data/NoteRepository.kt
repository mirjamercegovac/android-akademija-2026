package com.example.notesapp.data

import androidx.compose.runtime.mutableStateListOf
import com.example.notesapp.model.Note

class NoteRepository{
    private val notes = mutableStateListOf(
        Note(1, "Biljeska 1", "Opis prve biljeske.", "12.04.2026."),
        Note(2, "Biljeska 2", "Opis druge biljeske.", "12.04.2026."),
        Note(3, "Biljeska 3", "Opis trece biljeske.", "12.04.2026.")
    )

    fun getAllNotes(): List<Note> = notes.toList()

    fun getNoteById(id: Int): Note? = notes.firstOrNull { it.id == id }

    fun addNote(title: String, content: String, createdAt: String) {
        val newId = (notes.maxOfOrNull { it.id } ?: 0) + 1
        notes.add(
            Note(
                id = newId,
                title = title,
                content = content,
                createdAt = createdAt
            )
        )
    }

    fun updateNote(id: Int, title: String, content: String) {
        val index = notes.indexOfFirst { it.id == id }
        if (index != -1) {
            val oldNote = notes[index]
            notes[index] = oldNote.copy(
                title = title,
                content = content
            )
        }
    }
}