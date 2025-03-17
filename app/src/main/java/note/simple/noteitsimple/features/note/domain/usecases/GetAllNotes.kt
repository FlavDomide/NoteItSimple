package note.simple.noteitsimple.features.note.domain.usecases

import note.simple.noteitsimple.features.note.data.models.Note
import note.simple.noteitsimple.features.note.domain.INoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllNotes @Inject constructor(private val repository: INoteRepository) {
    suspend fun getAll(): Flow<List<Note>> = withContext(Dispatchers.IO) {
        repository.getAllNotes()
    }
}