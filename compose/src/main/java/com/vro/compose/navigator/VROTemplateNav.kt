package com.vro.compose.navigator

import androidx.navigation.NavController
import com.vro.compose.VROComposableActivity
import com.vro.navigation.VROBackResult
import com.vro.navigation.VRODestination

/**
 * Interface that defines the contract for handling navigation actions
 * in a template-based Compose screen architecture.
 *
 * Implementations of this interface manage forward and backward navigation
 * and should be injected into each template to handle navigation consistently.
 *
 * @param D The type of destination, extending [VRODestination], used for navigation targets.
 */
interface VROTemplateNav<D : VRODestination> {

    /**
     * Initializes the navigator with the required [activity] and [navController].
     *
     * This method must be called before performing any navigation actions.
     *
     * @param activity The host [VROComposableActivity], used for context-bound navigation operations.
     * @param navController The [NavController] that handles Compose navigation.
     */
    fun initialize(
        activity: VROComposableActivity,
        navController: NavController,
    )

    /**
     * Navigates to the specified [destination].
     *
     * @param destination The [VRODestination] representing the target screen or route.
     */
    fun navigate(destination: D)

    /**
     * Navigates back to the previous screen, optionally passing a result.
     *
     * @param result An optional [VROBackResult] to deliver to the previous screen.
     */
    fun navigateBack(result: VROBackResult?)
}
