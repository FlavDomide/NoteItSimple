package com.example.noteitsimple.features.note.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noteitsimple.R
import com.example.noteitsimple.common.common_components.BackButton
import com.example.noteitsimple.common.common_components.CommonTextField
import com.example.noteitsimple.common.navigation.Routes
import com.example.noteitsimple.features.note.viewmodels.NoteViewModel
import com.example.noteitsimple.utils.AppHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    navController: NavController, noteViewModel: NoteViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.add_note)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    BackButton(navController)
                },
                actions = {
                    TextButton(
                        onClick = {
                            if (title.isEmpty()) {
                                AppHelper.showToast(
                                    context, context.getString(R.string.add_at_least_a_title)
                                )
                            } else {
                                // Save Note into db
                                noteViewModel.saveNote(title = title, desc = description)
                                AppHelper.showToast(
                                    context, context.getString(R.string.note_saved_successfully)
                                )

                                // Navigate Home
                                navController.navigate(Routes.Home.name) {
                                    // Remove this page from the stack after navigating home
                                    popUpTo(Routes.Home.name) {
                                        inclusive = true
                                    }
                                }
                            }
                        }) {
                        Text(
                            text = "Save", style = MaterialTheme.typography.titleLarge.copy(
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
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CommonTextField(
                    value = title, onValueChange = { newValue ->
                        title = newValue
                    }, placeholder = {
                        Text(
                            text = stringResource(R.string.note_title),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }, singleLine = true, textStyle = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                CommonTextField(value = description, onValueChange = { newValue ->
                    description = newValue
                }, placeholder = {
                    Text(
                        text = stringResource(R.string.type_something),
                        style = MaterialTheme.typography.bodyMedium
                    )
                })
            }
        }
    }
}