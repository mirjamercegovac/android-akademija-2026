package com.example.notesapp.ui.di

import com.example.notesapp.ui.edittask.EditTaskViewModel
import com.example.notesapp.ui.login.LoginViewModel
import com.example.notesapp.ui.tasklist.TaskListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<LoginViewModel> {
        LoginViewModel(
            repository = get(),
            logger = get { parametersOf("LoginViewModel") }
        )
    }

    viewModel<TaskListViewModel> {
        TaskListViewModel(
            repository = get(),
            logger = get { parametersOf("TaskListViewModel") }
        )
    }

    viewModel<EditTaskViewModel> {
        EditTaskViewModel(
            repository = get(),
            logger = get { parametersOf("EditTaskViewModel") }
        )
    }
}