package com.sampleapp.styles.theme

import androidx.compose.runtime.Composable
import com.sampleapp.styles.colors.SampleColorScheme
import com.sampleapp.ui.main.SampleMainActivity.Companion.LocalActivityColors

object SampleAppTheme {
    val colors: SampleColorScheme
        @Composable get() = LocalActivityColors.current
}