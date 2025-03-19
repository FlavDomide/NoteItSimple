package note.simple.noteitsimple.features.home.views

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import note.simple.noteitsimple.R
import note.simple.noteitsimple.common.common_components.LoadingIndicator
import note.simple.noteitsimple.common.common_components.NotesGridView
import note.simple.noteitsimple.common.navigation.Routes
import note.simple.noteitsimple.features.note.viewmodels.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController, noteViewModel: NoteViewModel = hiltViewModel()
) {
    val noteListState = noteViewModel.noteListState.collectAsState()
    val noteValue = noteListState.value

    val configuration = LocalConfiguration.current
    val fabPaddingEnd = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        48.dp // Moves FAB up in landscape mode
    } else {
        0.dp // Normal padding for portrait
    }

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
        FloatingActionButton(
            onClick = {
                // Navigate to add note screen
                navController.navigate(Routes.AddNote.name)
            }, modifier = Modifier
                .padding(
                    end = WindowInsets.navigationBars.asPaddingValues()
                        .calculateEndPadding(LayoutDirection.Rtl) + fabPaddingEnd
                )
        ) {
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