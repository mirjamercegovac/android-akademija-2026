package com.example.notesapp.di

import org.koin.dsl.module

val loggingModule = module {
    factory { (tag: String) -> AppLogger(tag) }
}