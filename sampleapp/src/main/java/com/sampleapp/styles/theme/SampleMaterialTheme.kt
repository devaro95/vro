package com.sampleapp.styles.theme

import com.sampleapp.styles.colors.SampleMaterialDarkColors
import com.sampleapp.styles.colors.SampleMaterialLightColors
import com.sampleapp.styles.typography.SampleMaterialTypography
import com.vro.compose.theme.VROComposableMaterialTheme

val SampleMaterialTheme = VROComposableMaterialTheme(
    lightColors = SampleMaterialLightColors,
    darkColors = SampleMaterialDarkColors,
    typography = SampleMaterialTypography
)