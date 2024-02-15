package com.vro.compose.extensions

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.vro.compose.VROComposableTheme

@Composable
fun GeneratePreview(
    theme: VROComposableTheme? = null,
    content: @Composable () -> Unit,
) {
    theme?.let {
        MaterialTheme(
            colorScheme = if (isSystemInDarkTheme()) theme.darkColors else theme.lightColors,
            typography = theme.typography,
        ) {
            content.invoke()
        }
    } ?: run {
        content.invoke()
    }
}