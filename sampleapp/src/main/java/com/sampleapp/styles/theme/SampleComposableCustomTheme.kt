package com.sampleapp.styles.theme

import androidx.compose.material3.Typography
import com.sampleapp.styles.colors.SampleColorScheme
import com.vro.compose.theme.VROComposableCustomTheme

data class SampleComposableCustomTheme(
    override val lightColors: SampleColorScheme,
    override val darkColors: SampleColorScheme? = null,
    override val typography: Typography? = null,
) : VROComposableCustomTheme(
    lightColors,
    darkColors,
    typography,
)