package com.example.notesapp.data

//zadaca 4 - koristenje lazy

val noteRepository by lazy {
    NoteRepository()
}