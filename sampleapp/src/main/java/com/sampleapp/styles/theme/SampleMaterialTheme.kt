package com.sampleapp.styles.theme

import com.sampleapp.styles.colors.SampleMaterialDarkColors
import com.sampleapp.styles.colors.SampleMaterialLightColors
import com.sampleapp.styles.SampleTypography
import com.vro.compose.theme.VROComposableMaterialTheme

val SampleTheme = VROComposableMaterialTheme(
    lightColors = SampleMaterialLightColors,
    darkColors = SampleMaterialDarkColors,
    typography = SampleTypography
)