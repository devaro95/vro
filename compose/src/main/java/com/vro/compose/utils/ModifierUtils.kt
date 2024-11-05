package com.vro.compose.utils

import androidx.compose.foundation.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun Modifier.tabletModifier(modifier: Modifier.() -> Modifier): Modifier {
    return if (isTablet())
        this.then(modifier.invoke(this))
    else this
}

@Composable
fun Modifier.mobileModifier(modifier: Modifier.() -> Modifier): Modifier {
    return if (!isTablet())
        this.then(modifier.invoke(this))
    else this
}

@Composable
fun Modifier.vroVerticalScroll(removeFocus: Boolean = false): Modifier {
    val scrollState = rememberScrollState()
    if (scrollState.isScrollInProgress && removeFocus) LocalFocusManager.current.clearFocus()
    return this.verticalScroll(scrollState)
}