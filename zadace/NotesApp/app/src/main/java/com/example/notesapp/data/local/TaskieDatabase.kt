package com.example.notesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TaskEntity::class],
    version = 2,
    exportSchema = false
)
abstract class TaskieDatabase : RoomDatabase(){
    abstract fun taskDao(): TaskDao
}