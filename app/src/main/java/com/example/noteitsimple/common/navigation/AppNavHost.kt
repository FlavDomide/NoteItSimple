package com.example.noteitsimple.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.noteitsimple.features.home.views.HomeScreen
import com.example.noteitsimple.features.home.views.SearchNoteScreen
import com.example.noteitsimple.features.note.views.AddNoteScreen
import com.example.noteitsimple.features.note.views.NoteDetails

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home.name,
        modifier = modifier
    ) {
        composable(Routes.Home.name) { HomeScreen(navController) }
        composable(Routes.AddNote.name) { AddNoteScreen(navController) }
        composable(Routes.SearchNotes.name) { SearchNoteScreen(navController) }
        composable("${Routes.NoteDetails.name}/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            if (id != null) {
                NoteDetails(navController, noteId = id.toInt())
            }
        }
    }
}