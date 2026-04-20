package com.sampleapp.ui.main

import androidx.compose.ui.graphics.Color
import com.sampleapp.styles.theme.SampleComposableCustomTheme

class SampleSubMainActivity : SampleMainActivity() {
    override val customTheme: SampleComposableCustomTheme
        get() = super.customTheme.copy(
            lightColors = super.customTheme.lightColors.copy(
                primary = Color(0xFFCDDC39),
                background = Color(0xFFCDDC39)
            ),
            darkColors = super.customTheme.lightColors.copy(
                primary = Color(0xFFCDDC39),
                background = Color(0xFFCDDC39)
            ),
        )
}