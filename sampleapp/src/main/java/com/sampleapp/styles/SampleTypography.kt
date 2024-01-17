package com.sampleapp.styles

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sampleapp.R

val SampleTypography = Typography(
    bodySmall = TextStyle(
        color = White,
        fontSize = 14.sp,
        fontFamily = FontFamily(Font(R.font.main_font_light))
    ),
    bodyMedium = TextStyle(
        color = White,
        fontSize = 16.sp,
        fontFamily = FontFamily(Font(R.font.main_font_light))
    ),
    bodyLarge = TextStyle(
        color = White,
        fontSize = 18.sp,
        fontFamily = FontFamily(Font(R.font.main_font_light))
    ),
    titleSmall = TextStyle(
        color = White,
        fontSize = 20.sp,
        fontFamily = FontFamily(Font(R.font.main_font_medium))
    ),
    titleMedium = TextStyle(
        color = White,
        fontSize = 22.sp,
        fontFamily = FontFamily(Font(R.font.main_font_medium))
    ),
    headlineSmall = TextStyle(
        color = White,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily(Font(R.font.main_font_medium))
    ),
    headlineMedium = TextStyle(
        color = White,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily(Font(R.font.main_font_medium))
    )
)