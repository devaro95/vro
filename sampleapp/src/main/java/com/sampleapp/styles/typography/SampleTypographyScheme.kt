package com.sampleapp.styles.typography

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.vro.compose.theme.VROTypographyScheme

class SampleTypographyScheme(
    val bold: TextStyle = Bold,
    val regular: TextStyle = Regular,
    val semibold: TextStyle = SemiBold
) : VROTypographyScheme() {
    internal companion object {
        val Bold = TextStyle(
            fontWeight = FontWeight.Bold
        )
        val Regular = TextStyle(
            fontWeight = FontWeight.Normal
        )
        val SemiBold = TextStyle(
            fontWeight = FontWeight.SemiBold
        )
    }
}

val SampleCustomTypographyDefault = SampleTypographyScheme()