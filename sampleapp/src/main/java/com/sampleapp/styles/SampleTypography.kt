package com.sampleapp.styles

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sampleapp.R

val SampleTypography = Typography(
    bodySmall = TextStyle(
        color = Color.Black,
        fontSize = 14.sp,
        fontFamily = FontFamily(Font(R.font.main_font_light))
    ),
    bodyMedium = TextStyle(
        color = Color.Black,
        fontSize = 16.sp,
        fontFamily = FontFamily(Font(R.font.main_font_light))
    ),
    bodyLarge = TextStyle(
        color = Color.Black,
        fontSize = 18.sp,
        fontFamily = FontFamily(Font(R.font.main_font_light))
    ),
    titleSmall = TextStyle(
        color = Color.Black,
        fontSize = 20.sp,
        fontFamily = FontFamily(Font(R.font.main_font_medium))
    ),
    titleMedium = TextStyle(
        color = Color.Black,
        fontSize = 22.sp,
        fontFamily = FontFamily(Font(R.font.main_font_medium))
    ),
    headlineSmall = TextStyle(
        color = Color.Black,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily(Font(R.font.main_font_medium))
    ),
    headlineMedium = TextStyle(
        color = Color.Black,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily(Font(R.font.main_font_medium))
    )
)