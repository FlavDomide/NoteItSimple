package note.simple.noteitsimple.features.note.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import note.simple.noteitsimple.features.note.data.models.Note
import note.simple.noteitsimple.utils.Converters

@Database(entities = [Note::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}