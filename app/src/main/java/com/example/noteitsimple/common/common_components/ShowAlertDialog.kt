package com.example.noteitsimple.common.common_components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.noteitsimple.R

@Composable
fun ShowAlertDialog(
    title: String,
    body: String,
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit
) {
    AlertDialog(
        icon = {

        },
        title = {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
        },
        text = {
            Text(text = body, fontSize = getAlertDialogTextSize())
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirmRequest()
            }) {
                Text(text = stringResource(R.string.yes), fontSize = getAlertDialogTextSize())
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismissRequest()
            }) {
                Text(text = stringResource(R.string.no), fontSize = getAlertDialogTextSize())
            }
        })
}

@Composable
fun getAlertDialogTextSize(): TextUnit {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    val textSize = when {
        screenWidth < 360 -> 14.sp
        screenWidth < 600 -> 18.sp
        else -> 22.sp
    }
    return textSize
}