package com.sampleapp.styles.colors

import androidx.compose.ui.graphics.Color
import com.vro.compose.theme.VROColorScheme

val SampleCustomLightColors = lightCustomColorScheme(
    primary = Color(0xFF4E4D4D),
    background = Color(0xFFF8F8F6),
    tabBarSelectedItem = Color(0xFFEB5043),
    bottomBarColor = Color(0xFFF8F8F6),
    topBarColor = Color(0xFFF8F8F6)
)

val SampleCustomDarkColors = darkCustomColorScheme(
    primary = Color(0xFF4E4D4D),
    background = Color(0xFFF8F8F6),
    tabBarSelectedItem = Color(0xFFEB5043),
    bottomBarColor = Color(0xFFF8F8F6),
    topBarColor = Color(0xFFF8F8F6)
)

fun lightCustomColorScheme(
    primary: Color,
    background: Color,
    tabBarSelectedItem: Color,
    bottomBarColor: Color,
    topBarColor: Color
): SampleColorScheme = SampleColorScheme(
    primary = primary,
    background = background,
    bottomBarSelected = tabBarSelectedItem,
    bottomBarColor = bottomBarColor,
    topBarColor = topBarColor
)

fun darkCustomColorScheme(
    primary: Color,
    background: Color,
    tabBarSelectedItem: Color,
    bottomBarColor: Color,
    topBarColor: Color
): SampleColorScheme = SampleColorScheme(
    primary = primary,
    background = background,
    bottomBarSelected = tabBarSelectedItem,
    bottomBarColor = bottomBarColor,
    topBarColor = topBarColor
)

data class SampleColorScheme(
    val primary: Color,
    val bottomBarSelected: Color,
    val bottomBarColor: Color,
    val topBarColor: Color,
    override val background: Color,
) : VROColorScheme(background = background)