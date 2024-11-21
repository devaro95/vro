package com.vro.compose.preview

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vro.compose.VROComposableTheme

@Composable
internal fun GeneratePreview(
    theme: VROComposableTheme? = null,
    content: @Composable () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        theme?.let {
            MaterialTheme(
                colorScheme = if (isSystemInDarkTheme()) theme.darkColors else theme.lightColors,
                typography = theme.typography,
            ) {
                content()
            }
        } ?: run {
            content()
        }
    }
}