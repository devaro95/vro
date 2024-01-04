package com.vro.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

abstract class VROSimpleComposableActivity : ComponentActivity() {

    open val theme: VROComposableTheme? = null

    @Composable
    private fun CreateTheme(
        lightColors: ColorScheme,
        darkColors: ColorScheme,
        typography: Typography,
        content: @Composable () -> Unit,
    ) {
        MaterialTheme(
            colorScheme = if (isSystemInDarkTheme()) darkColors else lightColors,
            typography = typography,
        ) {
            CompositionLocalProvider(
                content = content
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            theme?.also {
                CreateTheme(it.lightColors, it.darkColors, it.typography) { CreateContent() }
            } ?: run { CreateContent() }
        }
    }

    @Composable
    abstract fun CreateContent()

}