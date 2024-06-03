package com.vro.compose.utils

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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
fun Modifier.vroVerticalScroll(): Modifier {
    return this.verticalScroll(rememberScrollState())
}