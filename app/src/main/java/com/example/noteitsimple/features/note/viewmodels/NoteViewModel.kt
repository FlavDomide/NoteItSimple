package com.example.noteitsimple.features.note.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteitsimple.features.note.data.models.Note
import com.example.noteitsimple.features.note.data.repositories.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {
    private val _noteListState = MutableStateFlow(NoteState<List<Note>>())
    val noteListState = _noteListState.asStateFlow()

    private val _noteByIdState = MutableStateFlow(NoteState<Note>())
    val noteByIdState = _noteByIdState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        getAllNotes()
    }

    fun getNoteById(id: Int) = viewModelScope.launch {
        _noteByIdState.update { it.copy(isLoading = true) }
        try {
            repository.getNoteById(id).collect({ note ->
                _noteByIdState.update { it.copy(isLoading = false, data = note) }
            })
        } catch (e: Exception) {
            Log.d("Get note By Id", "Get Note By Id error: $e")
            _noteByIdState.update { it.copy(isLoading = false, error = e.message) }
            return@launch
        }
    }

    fun saveNote(title: String, desc: String) = viewModelScope.launch {
        _noteListState.update {
            it.copy(isLoading = true)
        }
        try {
            val note = Note(
                id = 0,
                title = title,
                description = desc
            )
            repository.saveNote(note)
            _noteListState.update { it.copy(isLoading = false) }
        } catch (e: Exception) {
            Log.d("Save note", "Save note error: $e")
            _noteListState.update {
                it.copy(isLoading = false, error = e.message)
                return@launch
            }
        }
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        _isLoading.value = true
        _noteListState.update { it.copy(isLoading = true) }
        try {
            repository.updateNote(note)
            _noteListState.update { it.copy(isLoading = true) }
            _isLoading.value = false
        } catch (e: Exception) {
            Log.d("Update note", "Update note error: $e")
            _noteListState.update { it.copy(isLoading = false, error = e.message) }
            _isLoading.value = false
            return@launch
        }
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        _noteListState.update { it.copy(isLoading = true) }
        try {
            repository.deleteNote(note)
            _noteListState.update { it.copy(isLoading = false) }
        } catch (e: Exception) {
            Log.d("Delete note", "Delete note error: $e")
            _noteListState.update { it.copy(isLoading = false, error = e.message) }
            return@launch
        }
    }

    private fun getAllNotes() = viewModelScope.launch {
        try {
            repository.getAllNotes().collect { notes ->
                _noteListState.update { it.copy(isLoading = false, data = notes) }
            }
        } catch (e: Exception) {
            Log.d("Get all notes", "Get all notes error: $e")
            _noteListState.update { it.copy(isLoading = false, error = e.message) }
            return@launch
        }
    }
}

data class NoteState<T>(
    val data: T? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)