package com.example.noteitsimple.utils

import android.content.Context
import android.widget.Toast
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class AppHelper {
    companion object {
        fun getCurrentDate(): LocalDateTime {
            val now = Clock.System.now()
            return now.toLocalDateTime(TimeZone.currentSystemDefault())
        }

        fun showToast(context: Context, message: String) {
            Toast.makeText(
                context, message, Toast.LENGTH_SHORT
            ).show()
        }
    }
}