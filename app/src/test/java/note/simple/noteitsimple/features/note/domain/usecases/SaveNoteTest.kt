package note.simple.noteitsimple.features.note.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import note.simple.noteitsimple.features.note.data.models.Note
import note.simple.noteitsimple.features.note.data.repositories.NoteRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class SaveNoteTest {
    private lateinit var saveNote: SaveNote
    private lateinit var noteRepository: NoteRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        noteRepository = mock(NoteRepository::class.java)
        saveNote = SaveNote(noteRepository)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `Save note successfully`() {
        runTest(testDispatcher) {
            // Arrange
            val note = Note(
                id = 0,
                title = "Test Title",
                description = "Test Description",
                createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )

            // Act
            saveNote.save(note)
            advanceUntilIdle()

            // Assert
            verify(noteRepository).saveNote(note)
        }
    }

    @Test
    fun `Note with empty title`() {
        runTest(testDispatcher) {
            // Arrange
            val note = Note(
                id = 0,
                title = "",
                description = "Test Description",
                createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )

            // Act
            saveNote.save(note)
            advanceUntilIdle()

            // Assert
            verify(noteRepository).saveNote(note)
        }
    }


    @Test
    fun `Note with empty description`() {
        runTest(testDispatcher) {
            // Arrange
            val note = Note(
                id = 0,
                title = "Test Title",
                description = "",
                createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )

            // Act
            saveNote.save(note)
            advanceUntilIdle()

            // Assert
            verify(noteRepository).saveNote(note)
        }
    }

    @Test
    fun `Note with very long title`() {
        runTest(testDispatcher) {
            // Arrange
            val note = Note(
                id = 0,
                title = "Test Title".repeat(1000),
                description = "Test Description",
                createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )

            // Act
            saveNote.save(note)
            advanceUntilIdle()

            // Assert
            verify(noteRepository).saveNote(note)
        }
    }

    @Test
    fun `Note with very long description`() {
        runTest(testDispatcher) {
            // Arrange
            val note = Note(
                id = 0,
                title = "Test Title",
                description = "Test Description".repeat(1000),
                createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )

            // Act
            saveNote.save(note)
            advanceUntilIdle()

            // Assert
            verify(noteRepository).saveNote(note)
        }
    }

    @Test
    fun `Multiple saves in rapid succession`() {
        runTest(testDispatcher) {
            // Arrange
            val note = Note(
                id = 0,
                title = "Test Title",
                description = "Test Description",
                createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )

            val note1 = Note(
                id = 1,
                title = "Test Title 1",
                description = "Test Description 1",
                createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )

            // Act
            saveNote.save(note)
            saveNote.save(note1)
            advanceUntilIdle()

            // Assert
            verify(noteRepository).saveNote(note)
            verify(noteRepository).saveNote(note1)
        }
    }

    @Test
    fun `Save with special characters in title`() {
        runTest(testDispatcher) {
            // Arrange
            val note = Note(
                id = 0,
                title = "Test Title with !@#\$%^&*()",
                description = "Test Description",
                createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )

            // Act
            saveNote.save(note)
            advanceUntilIdle()

            // Assert
            verify(noteRepository).saveNote(note)
        }
    }

    @Test
    fun `Save with special characters in content`() {
        runTest(testDispatcher) {
            // Arrange
            val note = Note(
                id = 0,
                title = "Test Title",
                description = "Test Description with !@#\$%^&*()",
                createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )

            // Act
            saveNote.save(note)
            advanceUntilIdle()

            // Assert
            verify(noteRepository).saveNote(note)
        }
    }
}