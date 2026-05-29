package com.example.notesapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notesapp.ui.edittask.EditTaskScreen
import com.example.notesapp.ui.edittask.EditTaskViewModel
import com.example.notesapp.ui.login.LoginScreen
import com.example.notesapp.ui.login.LoginViewModel
import com.example.notesapp.ui.tasklist.TaskListScreen
import com.example.notesapp.ui.tasklist.TaskListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskieApp(){
    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            val loginViewModel: LoginViewModel = koinViewModel()

            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigate("task_list")
                }
            )
        }

        composable("task_list") {
            val taskListViewModel: TaskListViewModel = koinViewModel()

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
            val editTaskViewModel: EditTaskViewModel = koinViewModel()

            EditTaskScreen(
                taskId = null,
                viewModel = editTaskViewModel,
                onBackClick = { navController.popBackStack() },
                onDoneClick = { navController.popBackStack() }
            )
        }

        composable(
            route = "edit_task/{taskId}",
            arguments = listOf(
                navArgument("taskId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")

            val editTaskViewModel: EditTaskViewModel = koinViewModel()

            EditTaskScreen(
                taskId = taskId,
                viewModel = editTaskViewModel,
                onBackClick = { navController.popBackStack() },
                onDoneClick = { navController.popBackStack() }
            )
        }
    }

}