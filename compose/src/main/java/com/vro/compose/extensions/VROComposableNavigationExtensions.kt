/**
 * Package containing navigation-related extension functions.
 */

package com.vro.compose.extensions

import com.vro.compose.dialog.VROComposableBottomSheetContent
import com.vro.compose.dialog.VROComposableViewModelBottomSheetContent
import com.vro.compose.screen.VROScreen

/**
 * Generates a unique destination route for a [VROScreen] using its fully qualified class name.
 *
 * @return The fully qualified class name as a String, which serves as the navigation route
 *
 */
fun VROScreen<*, *>.destinationRoute(): String = this::class.java.name

/**
 * Generates a unique destination route for a [VROComposableBottomSheetContent] using its fully qualified class name.
 *
 * @return The fully qualified class name as a String, which serves as the navigation route
 *
 */
fun VROComposableBottomSheetContent<*>.destinationRoute(): String = this::class.java.name

/**
 * Generates a unique destination route for a [VROComposableViewModelBottomSheetContent] using its fully qualified class name.
 *
 * @return The fully qualified class name as a String, which serves as the navigation route
 *
 */
fun VROComposableViewModelBottomSheetContent<*, *>.destinationRoute(): String = this::class.java.name