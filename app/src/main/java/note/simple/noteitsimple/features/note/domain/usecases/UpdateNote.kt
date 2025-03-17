package note.simple.noteitsimple.features.note.domain.usecases

import note.simple.noteitsimple.features.note.data.models.Note
import note.simple.noteitsimple.features.note.domain.INoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateNote @Inject constructor(private val repository: INoteRepository) {
    suspend fun update(note: Note) = withContext(Dispatchers.IO) {
        repository.updateNote(note)
    }
}