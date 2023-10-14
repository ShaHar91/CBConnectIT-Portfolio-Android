package be.christiano.portfolio.app.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import be.christiano.portfolio.app.R

@Composable
fun ConfirmationDialog(
    title: String = stringResource(id = R.string.app_name),
    text: String,
    confirmButtonText: String,
    dismissButtonText: String,
    onDismissRequest: () -> Unit,
    onConfirmButtonClicked: () -> Unit,
    onDismissButtonClicked: () -> Unit = onDismissRequest,
) {
    AlertDialog(
        title = {
            Text(text = title)
        },
        text = {
            Text(text = text)
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirmButtonClicked) {
                Text(confirmButtonText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissButtonClicked) {
                Text(dismissButtonText)
            }
        }
    )
}

@Composable
fun InformativeDialog(
    title: String = stringResource(id = R.string.app_name),
    text: String,
    confirmButtonText: String,
    onDismissRequest: () -> Unit,
    onConfirmButtonClicked: () -> Unit = onDismissRequest
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        text = {
            Text(text = text)
        },
        title = {
            Text(text = title)
        },
        confirmButton = {
            TextButton(onClick = onConfirmButtonClicked) {
                Text(confirmButtonText)
            }
        }
    )
}