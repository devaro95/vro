package com.sampleapp.styles.theme

import com.sampleapp.styles.typography.SampleCustomTypographyDefault
import com.sampleapp.styles.colors.SampleCustomDarkColors
import com.sampleapp.styles.colors.SampleCustomLightColors

val SampleCustomTheme = SampleComposableCustomTheme(
    lightColors = SampleCustomLightColors,
    darkColors = SampleCustomDarkColors,
    typography = SampleCustomTypographyDefault
)