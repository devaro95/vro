package com.vro.compose.utils

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