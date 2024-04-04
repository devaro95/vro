package com.sampleapp.styles

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val White = Color(0xffffffff)
private val mainBlue = Color(0xff00798C)
private val background = Color(0xffffffff)

val SampleLightColors = lightColorScheme(
    primary = White,
    secondary = mainBlue,
    background = background
)

val SampleDarkColors = darkColorScheme(
    primary = White,
    secondary = mainBlue,
    background = background
)