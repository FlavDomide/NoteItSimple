package com.example.noteitsimple.features.home.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noteitsimple.R
import com.example.noteitsimple.common.common_components.LoadingIndicator
import com.example.noteitsimple.common.common_components.NotesGridView
import com.example.noteitsimple.common.navigation.Routes
import com.example.noteitsimple.features.note.viewmodels.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController, noteViewModel: NoteViewModel = hiltViewModel()
) {
    val noteListState = noteViewModel.noteListState.collectAsState()
    val noteValue = noteListState.value

    // Scaffold holds together different parts of the UI such as app bars and floating action buttons
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(stringResource(R.string.my_notes)) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.background
            ),
            actions = {
                IconButton(onClick = {
                    navController.navigate(Routes.SearchNotes.name)
                }) {
                    Icon(
                        Icons.Rounded.Search,
                        contentDescription = stringResource(R.string.search_button_description),
                        tint = MaterialTheme.colorScheme.background
                    )
                }
            }
        )
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            // Navigate to add note screen
            navController.navigate(Routes.AddNote.name)
        }) {
            Icon(Icons.Rounded.Add, contentDescription = stringResource(R.string.add_note_button))
        }
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            when {
                noteValue.isLoading -> {
                    LoadingIndicator()
                }

                noteValue.error != null -> {
                    Text(stringResource(R.string.something_went_wrong))
                }

                noteValue.data != null -> {
                    val notes = noteValue.data
                    NotesGridView(notes, navController)
                }
            }
        }
    }
}