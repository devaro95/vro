/**
 * Package containing navigation-related extension functions.
 */

package com.vro.compose.extensions

import com.vro.compose.dialog.VROComposableBottomSheetContent
import com.vro.compose.dialog.VROComposableViewModelBottomSheetContent
import com.vro.compose.screen.VROScreen
import com.vro.compose.screen.VROScreenBase
import com.vro.compose.template.VROTemplate
import kotlin.reflect.KClass

/**
 * Generates a unique destination route for a Class using its fully qualified class name.
 *
 * @return The fully qualified class name as a String, which serves as the navigation route
 *
 */
fun <T : Any> KClass<T>.destinationRoute(): String = this.java.name
