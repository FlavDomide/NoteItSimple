package note.simple.noteitsimple.common.common_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import note.simple.noteitsimple.R
import androidx.navigation.NavController
import note.simple.noteitsimple.common.navigation.Routes
import note.simple.noteitsimple.features.note.data.models.Note

@Composable
fun NotesGridView(
    notes: List<Note>,
    navController: NavController
) {
    if (notes.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) { Text(stringResource(R.string.there_are_no_notes_available)) }
    } else {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(count = 2),
            modifier = Modifier.fillMaxSize(),
            verticalItemSpacing = 4.dp,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(notes) { note ->
                NoteCard(note, onclick = {
                    navController.navigate(
                        "${Routes.NoteDetails.name}/${note.id}"
                    )
                })
            }
        }
    }
}