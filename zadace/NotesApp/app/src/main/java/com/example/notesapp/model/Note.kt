package com.example.notesapp.model

//zadaca 4 - izmjene (Note entitet koji sadrži naslov, sadržaj i datum kreiranja)
data class Note (
    val id: Int,
    val title: String,
    val content: String,
    val createdAt: String
)