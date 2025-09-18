package com.vro.compose.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Typography

/**
 * A data class representing a theme configuration for a Compose-based UI.
 * Contains color schemes for both light and dark modes, along with typography settings.
 *
 * @property lightColors The color scheme for light theme mode
 * @property darkColors The color scheme for dark theme mode
 * @property typography The typography configuration for the theme
 */
data class VROComposableMaterialTheme(
    val lightColors: ColorScheme? = null,
    val darkColors: ColorScheme? = null,
    val typography: Typography? = null,
)