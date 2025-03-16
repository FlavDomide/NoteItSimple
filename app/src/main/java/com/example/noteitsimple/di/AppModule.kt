package com.example.noteitsimple.di

import android.content.Context
import androidx.room.Room
import com.example.noteitsimple.features.note.data.datasource.NoteDatabase
import com.example.noteitsimple.features.note.data.repositories.NoteRepository
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
}
