package com.sampleapp.styles.theme

import com.sampleapp.styles.typography.SampleCustomTypographyDefault
import com.sampleapp.styles.typography.SampleTypographyScheme
import com.sampleapp.styles.colors.SampleColorScheme
import com.vro.compose.theme.VROComposableCustomTheme

data class SampleComposableCustomTheme(
    override val lightColors: SampleColorScheme,
    override val darkColors: SampleColorScheme? = null,
    override val typography: SampleTypographyScheme = SampleCustomTypographyDefault,
) : VROComposableCustomTheme(
    lightColors,
    darkColors,
    typography,
)