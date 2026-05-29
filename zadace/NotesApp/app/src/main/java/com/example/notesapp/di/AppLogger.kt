package com.example.notesapp.di

import android.util.Log

class AppLogger (
    private val tag: String
){
    fun debug(message: String) {
        Log.d(tag, message)
    }

    fun error(message: String, throwable: Throwable? = null) {
        Log.e(tag, message, throwable)
    }
}