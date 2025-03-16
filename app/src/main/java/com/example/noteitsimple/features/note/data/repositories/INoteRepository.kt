package com.example.noteitsimple.features.note.data.repositories

import com.example.noteitsimple.features.note.data.models.Note
import kotlinx.coroutines.flow.Flow

interface INoteRepository {
    suspend fun saveNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun getNoteById(id: Int): Flow<Note?>
    suspend fun getAllNotes(): Flow<List<Note>>
}