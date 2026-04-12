package com.example.notesapp

//ZASLON 1: Svaka biljeska ima: id, naslov, opis, datum
data class Note (
    val id: Int,
    val title: String,
    val description: String,
    val date: String = ""
)