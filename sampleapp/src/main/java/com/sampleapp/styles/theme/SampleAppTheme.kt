package com.sampleapp.styles.theme

import androidx.compose.runtime.Composable
import com.sampleapp.styles.typography.SampleTypographyScheme
import com.sampleapp.styles.colors.SampleColorScheme
import com.sampleapp.ui.main.SampleMainActivity.Companion.LocalActivityColors
import com.sampleapp.ui.main.SampleMainActivity.Companion.LocalActivityTypography

object SampleAppTheme {
    val colors: SampleColorScheme
        @Composable get() = LocalActivityColors.current

    val typography: SampleTypographyScheme
        @Composable get() = LocalActivityTypography.current
}