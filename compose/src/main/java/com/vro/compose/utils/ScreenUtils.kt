package com.vro.compose.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

/**
 * Determines if the current device should be considered a tablet based on screen width.
 *
 * @return true if the device screen width is 600dp or greater (tablet size),
 *         false otherwise (phone size)
 *
 * @SuppressLint("NewApi") Configuration APIs are safely accessed through Compose
 */
@SuppressLint("NewApi")
@Composable
fun isTablet(): Boolean {
    return LocalConfiguration.current.screenWidthDp >= 600
}