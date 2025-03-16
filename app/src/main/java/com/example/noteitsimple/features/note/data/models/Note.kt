package com.example.noteitsimple.features.note.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteitsimple.utils.AppHelper
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    var description: String,
    val createdAt: LocalDateTime = AppHelper.getCurrentDate()
)
