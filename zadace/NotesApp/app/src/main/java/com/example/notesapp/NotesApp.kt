package com.example.notesapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun NotesApp(){
    val navController = rememberNavController()

    // ZASLON 1
    // State koji cuva listu biljeski
    var notesList by remember {
        mutableStateOf(
            listOf(
                Note(1, "Biljeska 1", "Opis prve biljeske.", "12.04.2026."),
                Note(2, "Biljeska 2", "Opis druge biljeske.", "12.04.2026."),
                Note(3, "Biljeska 3", "Opis trece biljeske.", "12.04.2026.")

            )
        )
    }

    NavHost(
        navController = navController,
        startDestination = "list_screen"
    ){
        composable ("list_screen"){
            NotesListScreen(
                notesList = notesList,
                // Gumb za dodavanje nove biljeske
                // Klikom se otvara Zaslon 2 za unos nove biljeske
                onAddClick = {
                    navController.navigate("edit_screen/-1")
                },
                // Klik na pojedinu biljesku otvara Zaslon 2
                onNoteClick = {
                    noteId -> navController.navigate("edit_screen/$noteId")
                }
            )
        }

        composable(
            route = "edit_screen/{noteId}",
            arguments = listOf(
                navArgument("noteId"){
                    type = NavType.IntType
                }
            )
        ){ backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: -1
            val selectedNote = notesList.firstOrNull { it.id == noteId }

            NoteEditScreen(
                existingNote = selectedNote,
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveClick = { title, description ->
                    if (selectedNote == null) {
                        val newId = (notesList.maxOfOrNull { it.id } ?: 0) + 1
                        notesList = notesList + Note(

                            // ZASLON 2 - Dodavanje nove biljeske
                            id = newId,
                            title = title,
                            description = description,
                            date = "12.04.2026."
                        )
                    } else {
                        // ZASLON 2 - Uredjivanje postojece biljeske
                        notesList = notesList.map { note ->
                            if (note.id == selectedNote.id) {
                                note.copy(title = title, description = description)
                            } else {
                                note
                            }
                        }
                    }

                    navController.popBackStack()
                }
            )
        }
    }
}