package com.example.noteitsimple.features.note.domain.usecases

import com.example.noteitsimple.features.note.data.models.Note
import com.example.noteitsimple.features.note.domain.INoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteNote @Inject constructor(private val repository: INoteRepository) {
    suspend fun delete(note: Note) = withContext(Dispatchers.IO) {
        repository.deleteNote(note)
    }
}