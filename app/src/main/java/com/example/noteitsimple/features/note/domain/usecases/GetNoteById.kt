package com.example.noteitsimple.features.note.domain.usecases

import com.example.noteitsimple.features.note.data.models.Note
import com.example.noteitsimple.features.note.domain.INoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNoteById @Inject constructor(private val repository: INoteRepository) {
    suspend fun getNote(id: Int): Flow<Note?> = withContext(Dispatchers.IO) {
        repository.getNoteById(id)
    }
}