package com.example.noteitsimple.features.note.domain.usecases

import com.example.noteitsimple.features.note.data.models.Note
import com.example.noteitsimple.features.note.domain.INoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateNote @Inject constructor(private val repository: INoteRepository) {
    suspend fun update(note: Note) = withContext(Dispatchers.IO) {
        repository.updateNote(note)
    }
}