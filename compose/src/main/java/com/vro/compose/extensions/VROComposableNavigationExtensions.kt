/**
 * Package containing navigation-related extension functions.
 */

package com.vro.compose.extensions

import com.vro.compose.dialog.VROComposableBottomSheetContent
import com.vro.compose.dialog.VROComposableViewModelBottomSheetContent
import com.vro.compose.screen.VROScreen
import com.vro.compose.screen.VROScreenBase
import com.vro.compose.template.VROTemplate

/**
 * Generates a unique destination route for a [VROScreenBase] using its fully qualified class name.
 *
 * @return The fully qualified class name as a String, which serves as the navigation route
 *
 */
fun VROScreenBase<*, *>.destinationRoute(): String = this::class.java.name

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

/**
 * Generates a unique destination route for a [VROTemplate] using its fully qualified class name.
 *
 * @return The fully qualified class name as a String, which serves as the navigation route
 *
 */
fun VROTemplate<*, *, *, *>.destinationRoute(): String = this::class.java.name
