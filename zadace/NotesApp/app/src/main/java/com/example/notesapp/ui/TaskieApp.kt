package com.example.notesapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notesapp.AppContainer
import com.example.notesapp.ui.edittask.EditTaskScreen
import com.example.notesapp.ui.edittask.EditTaskViewModel
import com.example.notesapp.ui.login.LoginScreen
import com.example.notesapp.ui.login.LoginViewModel
import com.example.notesapp.ui.tasklist.TaskListScreen
import com.example.notesapp.ui.tasklist.TaskListViewModel
import com.example.notesapp.viewmodel.EditTaskViewModelFactory
import com.example.notesapp.viewmodel.LoginViewModelFactory
import com.example.notesapp.viewmodel.TaskListViewModelFactory

@Composable
fun TaskieApp(){
    val navController = rememberNavController()
    val appContainer = remember { AppContainer() }

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            val loginViewModel: LoginViewModel = viewModel(
                factory = LoginViewModelFactory(appContainer.taskRepository)
            )

            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigate("task_list")
                }
            )
        }

        composable("task_list") {
            val taskListViewModel: TaskListViewModel = viewModel(
                factory = TaskListViewModelFactory(appContainer.taskRepository)
            )
            TaskListScreen(
                viewModel = taskListViewModel,
                onAddClick = {
                    navController.navigate("edit_task")
                },
                onTaskClick = { taskId ->
                    navController.navigate("edit_task/$taskId")
                }
            )
        }

        composable("edit_task") {
            val editTaskViewModel: EditTaskViewModel = viewModel(
                factory = EditTaskViewModelFactory(appContainer.taskRepository)
            )

            EditTaskScreen(
                taskId = null,
                viewModel = editTaskViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onDoneClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "edit_task/{taskId}",
            arguments = listOf(
                navArgument("taskId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")

            val editTaskViewModel: EditTaskViewModel = viewModel(
                factory = EditTaskViewModelFactory(appContainer.taskRepository)
            )

            EditTaskScreen(
                taskId = taskId,
                viewModel = editTaskViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onDoneClick = {
                    navController.popBackStack()
                }
            )
        }
    }

}