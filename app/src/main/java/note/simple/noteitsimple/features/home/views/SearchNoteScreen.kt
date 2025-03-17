package note.simple.noteitsimple.features.home.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import note.simple.noteitsimple.R
import note.simple.noteitsimple.common.common_components.BackButton
import note.simple.noteitsimple.common.common_components.LoadingIndicator
import note.simple.noteitsimple.common.common_components.NotesGridView
import note.simple.noteitsimple.common.common_components.SearchField
import note.simple.noteitsimple.features.note.data.models.Note
import note.simple.noteitsimple.features.note.viewmodels.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchNoteScreen(
    navController: NavController, noteViewModel: NoteViewModel = hiltViewModel()
) {
    val noteListState = noteViewModel.noteListState.collectAsState()
    val noteValue = noteListState.value

    var query by remember { mutableStateOf("") }

    val allNotes = remember { mutableStateListOf<Note>() }

    LaunchedEffect(noteValue) {
        allNotes.addAll(noteValue.data ?: emptyList())
    }

    val filteredNotes = if (query.isEmpty()) {
        allNotes
    } else {
        allNotes.filter { note ->
            note.title.lowercase().contains(query.lowercase()) ||
                    note.description.lowercase().contains(query.lowercase())
        }
    }

    // Scaffold holds together different parts of the UI such as app bars and floating action buttons
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(stringResource(R.string.search_notes)) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.background
            ),
            navigationIcon = {
                BackButton(navController)
            })
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SearchField(
                searchQuery = query,
                onQueryChanged = { newValue ->
                    query = newValue
                },
                onQueryClear = {
                    query = ""
                },
            )

            Spacer(modifier = Modifier.height(10.dp))

            when {
                noteValue.isLoading -> {
                    LoadingIndicator()
                }

                noteValue.error != null -> {
                    Text(stringResource(R.string.something_went_wrong))
                }

                noteValue.data != null -> {
                    NotesGridView(filteredNotes, navController)
                }
            }
        }
    }
}