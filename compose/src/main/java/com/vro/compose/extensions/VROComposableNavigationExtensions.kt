@file:JvmName("VRONavExtensionsKt")

package com.vro.compose.extensions

import com.vro.compose.dialog.VROComposableBottomSheetContent
import com.vro.compose.screen.VROScreen

fun VROScreen<*, *>.destinationRoute(): String = this::class.java.name

fun VROComposableBottomSheetContent<*, *>.destinationRoute(): String = this::class.java.name