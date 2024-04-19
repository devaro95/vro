package com.vro.compose.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@SuppressLint("NewApi")
@Composable
fun isTablet(): Boolean {
    return LocalConfiguration.current.screenWidthDp >= 600
}