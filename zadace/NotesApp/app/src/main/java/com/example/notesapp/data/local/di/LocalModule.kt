package com.example.notesapp.data.local.di

import androidx.room.Room
import com.example.notesapp.data.local.TaskieDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            TaskieDatabase::class.java,
            "taskie_database"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    single { get<TaskieDatabase>().taskDao() }
}