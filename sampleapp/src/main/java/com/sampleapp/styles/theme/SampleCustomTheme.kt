package com.sampleapp.styles.theme

import com.sampleapp.styles.SampleTypography
import com.sampleapp.styles.colors.SampleCustomDarkColors
import com.sampleapp.styles.colors.SampleCustomLightColors

val SampleCustomTheme = SampleComposableCustomTheme(
    lightColors = SampleCustomLightColors,
    darkColors = SampleCustomDarkColors,
    typography = SampleTypography
)