package note.simple.noteitsimple.di

import android.content.Context
import androidx.room.Room
import note.simple.noteitsimple.features.note.data.datasource.NoteDatabase
import note.simple.noteitsimple.features.note.data.repositories.NoteRepository
import note.simple.noteitsimple.features.note.domain.usecases.DeleteNote
import note.simple.noteitsimple.features.note.domain.usecases.GetAllNotes
import note.simple.noteitsimple.features.note.domain.usecases.GetNoteById
import note.simple.noteitsimple.features.note.domain.usecases.SaveNote
import note.simple.noteitsimple.features.note.domain.usecases.UpdateNote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(
        @ApplicationContext context: Context
    ): NoteDatabase = Room.databaseBuilder(
        context, NoteDatabase::class.java, "note_db"
    ).build()


    @Provides
    @Singleton
    fun provideRepository(db: NoteDatabase): NoteRepository = NoteRepository(db)

    @Provides
    @Singleton
    fun provideSaveNote(repository: NoteRepository): SaveNote = SaveNote(repository)

    @Provides
    @Singleton
    fun provideUpdateNote(repository: NoteRepository): UpdateNote = UpdateNote(repository)

    @Provides
    @Singleton
    fun provideDeleteNote(repository: NoteRepository): DeleteNote = DeleteNote(repository)

    @Provides
    @Singleton
    fun provideGetNoteById(repository: NoteRepository): GetNoteById = GetNoteById(repository)

    @Provides
    @Singleton
    fun provideGetAllNotes(repository: NoteRepository): GetAllNotes = GetAllNotes(repository)
}
