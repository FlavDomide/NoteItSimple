package note.simple.noteitsimple.features.note.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import note.simple.noteitsimple.R
import note.simple.noteitsimple.common.common_components.BackButton
import note.simple.noteitsimple.common.common_components.CommonTextField
import note.simple.noteitsimple.common.common_components.LoadingIndicator
import note.simple.noteitsimple.common.common_components.ShowAlertDialog
import note.simple.noteitsimple.common.navigation.Routes
import note.simple.noteitsimple.features.note.viewmodels.NoteViewModel
import note.simple.noteitsimple.utils.AppHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetails(
    navController: NavController, noteId: Int, noteViewModel: NoteViewModel = hiltViewModel()
) {
    val noteByIdState = noteViewModel.noteByIdState.collectAsState()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val context = LocalContext.current
    val isLoading by noteViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        noteViewModel.getNoteById(noteId)
    }

    val noteByIdValue = noteByIdState.value

    LaunchedEffect(noteByIdValue) {
        title = noteByIdValue.data?.title ?: ""
        description = noteByIdValue.data?.description ?: ""
    }

    var showAlertDialog by remember { mutableStateOf(value = false) }
    var isHidden by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.edit_note)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    BackButton(navController)
                },
                actions = {
                    IconButton(onClick = {
                        showAlertDialog = true
                    }) {
                        Icon(
                            Icons.Rounded.Delete,
                            contentDescription = stringResource(R.string.delete_note_button),
                            tint = MaterialTheme.colorScheme.background
                        )
                    }

                    TextButton(
                        onClick = {
                            val oldNote = noteByIdValue.data
                            if (oldNote != null) {
                                if (title.isEmpty()) {
                                    AppHelper.showToast(
                                        context, context.getString(R.string.add_at_least_a_title)
                                    )
                                } else {
                                    // Update Note into db
                                    oldNote.title = title
                                    oldNote.description = description
                                    noteViewModel.updateNote(oldNote)
                                    AppHelper.showToast(
                                        context,
                                        context.getString(R.string.note_updated_successfully)
                                    )
                                }
                            }
                        }) {
                        Text(
                            text = stringResource(R.string.update),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.background
                            )
                        )
                    }
                })
        }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            when {
                noteByIdValue.isLoading -> {
                    LoadingIndicator()
                }

                noteByIdValue.error != null -> {
                    Text(stringResource(R.string.something_went_wrong))
                }

                noteByIdValue.data != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .imePadding(),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        CommonTextField(
                            value = title,
                            onValueChange = { newValue ->
                                title = newValue
                            },
                            placeholder = {
                                Text(
                                    text = stringResource(R.string.note_title),
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = colorResource(R.color.light_gray)
                                )
                            },
                            singleLine = true,
                            textStyle = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            hide = isHidden
                        )
                        CommonTextField(value = description, onValueChange = { newValue ->
                            description = newValue
                        }, placeholder = {
                            Text(
                                text = stringResource(R.string.type_something),
                                style = MaterialTheme.typography.bodyMedium,
                                color = colorResource(R.color.light_gray)
                            )
                        }, hide = isHidden)
                    }

                    if (showAlertDialog) {
                        val note = noteByIdValue.data
                        ShowAlertDialog(
                            title = stringResource(R.string.delete_note),
                            body = stringResource(R.string.are_you_sure_to_delete_note),
                            onDismissRequest = { showAlertDialog = false },
                            onConfirmRequest = {
                                noteViewModel.deleteNote(note)
                                AppHelper.showToast(
                                    context, context.getString(R.string.note_deleted)
                                )

                                // Navigate Home
                                navController.navigate(Routes.Home.name) {
                                    // Remove this page from the stack after navigating home
                                    popUpTo(Routes.Home.name) {
                                        inclusive = true
                                    }
                                }
                            })
                    }

                    if (isLoading) {
                        LoadingIndicator()
                        isHidden = true
                    } else {
                        isHidden = false
                    }
                }
            }
        }
    }
}