package com.vro.compose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import platform.UIKit.UIDevice
import platform.UIKit.UIUserInterfaceIdiomPad

@Composable
actual fun isTablet(): Boolean {
    return UIDevice.currentDevice.userInterfaceIdiom == UIUserInterfaceIdiomPad
}
