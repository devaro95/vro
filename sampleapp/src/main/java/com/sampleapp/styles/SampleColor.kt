package com.sampleapp.styles

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val mainBlue = Color(0xFF26AAC1)
private val background = Color(0xffffffff)
val Skeleton = Color(0x4DBEBEBE)

val SampleLightColors = lightColorScheme(
    primary = Color.Black,
    secondary = mainBlue,
    background = background
)

val SampleDarkColors = darkColorScheme(
    primary = Color.Black,
    secondary = mainBlue,
    background = background
)