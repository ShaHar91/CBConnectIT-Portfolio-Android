package be.cbconnectit.portfolio.app.ui.base

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class BaseComposeViewModel : ViewModel() {
    private var snackbarMessageState by mutableStateOf<SnackbarMessage>(Empty)

    @Composable
    fun CreateSnackBarHost() {
        val snackBarHostState = remember {
            SnackbarHostState()
        }

        val context = LocalContext.current

        LaunchedEffect(key1 = snackbarMessageState) {
            if (snackbarMessageState !is Empty) {
                snackBarHostState.showSnackbar(snackbarMessageState.getMessage(context) ?: "Unknown error")
                snackbarMessageState = Empty
            }
        }

        SnackbarHost(hostState = snackBarHostState)
    }

    fun showSnackbar(snackbarMessage: SnackbarMessage) = viewModelScope.launch {
        snackbarMessageState = snackbarMessage
    }

    fun showSnackbar(message: String?) = viewModelScope.launch {
        snackbarMessageState = SnackbarMessage(message = message ?: return@launch)
    }
}