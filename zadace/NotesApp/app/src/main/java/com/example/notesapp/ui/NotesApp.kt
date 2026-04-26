package com.example.notesapp.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notesapp.data.noteRepository
import com.example.notesapp.viewmodel.NoteEditViewModel
import com.example.notesapp.viewmodel.NoteViewModelFactory
import com.example.notesapp.viewmodel.NotesListViewModel

@Composable
fun NotesApp(){
    val navController = rememberNavController()
    val factory = NoteViewModelFactory(noteRepository)

    NavHost(
        navController = navController,
        startDestination = "list_screen"
    ){
        composable("list_screen"){
            val listViewModel: NotesListViewModel = viewModel(factory = factory)

            NotesListScreen(
                notesList = listViewModel.getNotes(),
                onAddClick = {
                    navController.navigate("edit_screen/-1")
                },
                onNoteClick = { noteId ->
                    navController.navigate("edit_screen/$noteId")
                }
            )
        }
        composable(
            route = "edit_screen/{noteId}",
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.IntType
                }
            )
        ){ backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: -1
            val editViewModel: NoteEditViewModel = viewModel(factory = factory)

            NoteEditScreen(
                noteId = noteId,
                viewModel = editViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onDoneClick = {
                    editViewModel.saveNote()
                    navController.popBackStack()
                }
            )

        }
    }

}