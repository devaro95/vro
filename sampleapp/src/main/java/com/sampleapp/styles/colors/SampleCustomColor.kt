package com.sampleapp.styles.colors

import androidx.compose.ui.graphics.Color
import com.vro.compose.theme.VROColorScheme

val SampleCustomLightColors = lightCustomColorScheme(
    primary = Color(0xFF040EFF),
    background =  Color(0xFF040EFF),
)

val SampleCustomDarkColors = darkCustomColorScheme(
    primary =  Color(0xFF040EFF),
    background =  Color(0xFF040EFF),
)

fun lightCustomColorScheme(
    primary: Color,
    background: Color,
): SampleColorScheme = SampleColorScheme(
    primary = primary,
    background = background
)

fun darkCustomColorScheme(
    primary: Color,
    background: Color,
): SampleColorScheme = SampleColorScheme(
    primary = primary,
    background = background
)

data class SampleColorScheme(
    val primary: Color,
    override val background: Color,
) : VROColorScheme(background = background)