package com.vro.compose.utils

import android.annotation.SuppressLint
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalConfiguration
import kotlinx.coroutines.launch

@SuppressLint("NewApi")
@Composable
fun isTablet(): Boolean {
    return LocalConfiguration.current.screenWidthDp >= 600
}

@Composable
fun SnackbarHostState.ShowSnackBar(
    message: String,
    actionLabel: String? = null,
    cancelable: Boolean = true,
    duration: SnackbarDuration = SnackbarDuration.Short
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        scope.launch {
            showSnackbar(
                message = message,
                actionLabel = actionLabel,
                withDismissAction = cancelable,
                duration = duration
            ).also {
                when (it) {
                    SnackbarResult.Dismissed -> {
                    }

                    else -> Unit
                }
            }
        }
    }
}