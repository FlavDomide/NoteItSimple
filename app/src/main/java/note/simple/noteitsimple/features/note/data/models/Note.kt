package note.simple.noteitsimple.features.note.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import note.simple.noteitsimple.utils.AppHelper
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    var description: String,
    val createdAt: LocalDateTime = AppHelper.getCurrentDate()
)
