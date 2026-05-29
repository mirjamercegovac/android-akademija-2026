package com.example.notesapp.data.repository.di

import com.example.notesapp.data.repository.TaskRepository
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val repositoryModule = module {
    single {
        TaskRepository(
            taskDao = get(),
            api = get(),
            logger = get { parametersOf("TaskRepository") }
        )
    }
}