package com.example.noteitsimple.features.note.data.repositories

import com.example.noteitsimple.features.note.data.datasource.NoteDatabase
import com.example.noteitsimple.features.note.data.models.Note
import com.example.noteitsimple.features.note.domain.INoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepository(db: NoteDatabase) : INoteRepository {
    private val dao = db.noteDao()

    override suspend fun saveNote(note: Note) {
        return dao.insert(note)
    }

    override suspend fun updateNote(note: Note) {
        return dao.update(note)
    }

    override suspend fun deleteNote(note: Note) {
        return dao.delete(note)
    }

    override suspend fun getNoteById(id: Int): Flow<Note?> {
        return dao.getNoteById(id)
    }

    override suspend fun getAllNotes(): Flow<List<Note>> {
        return dao.getAllNotes()
    }
}