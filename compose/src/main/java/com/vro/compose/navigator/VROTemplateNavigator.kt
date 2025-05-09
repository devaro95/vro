package com.vro.compose.navigator

import android.content.Intent
import androidx.navigation.NavController
import com.vro.compose.VROComposableActivity
import com.vro.compose.dialog.VROComposableBottomSheetContent
import com.vro.compose.dialog.VROComposableViewModelBottomSheetContent
import com.vro.compose.extensions.destinationRoute
import com.vro.compose.screen.VROScreen
import com.vro.navigation.*
import com.vro.navstarter.VRONavStarter

/**
 * Abstract base class for handling navigation logic in a template-based screen.
 *
 * This class is responsible for managing navigation events, including:
 * - Navigating to new screens
 * - Navigating back
 * - Handling bottom sheets and activities
 * - Configuring navigation with a [NavController]
 *
 * It implements the [VROTemplateNav] interface, providing concrete methods to initialize
 * the navigator and perform navigation operations.
 *
 * @param D The type of destination, extending [VRODestination], used for navigation targets.
 */
abstract class VROTemplateNavigator<D : VRODestination> : VROTemplateNav<D> {

    /** The [NavController] used for navigating between destinations. */
    lateinit var navController: NavController

    /** The [VROComposableActivity] hosting the screen. */
    lateinit var activity: VROComposableActivity

    /**
     * Initializes the navigator with the given activity and NavController.
     *
     * This method must be called before any navigation actions can occur.
     *
     * @param activity The activity hosting the screen, used for context-bound navigation.
     * @param navController The NavController that handles navigation.
     */
    override fun initialize(
        activity: VROComposableActivity,
        navController: NavController,
    ) {
        this.activity = activity
        this.navController = navController
    }

    /**
     * Navigates back to the previous screen, optionally passing a result.
     *
     * If there is no previous screen in the stack, the current activity is finished.
     *
     * @param result The [VROBackResult] to be passed back to the previous screen, if any.
     */
    override fun navigateBack(result: VROBackResult?) {
        result?.let {
            putResultParam(result.id, result)
        }
        navController.popBackStack().also { hasBack ->
            if (!hasBack) finish()
        }
    }

    /**
     * Finishes the current activity.
     */
    fun finish() = activity.finish()

    /**
     * Starts a new activity with the provided [intent].
     *
     * @param intent The [Intent] to launch the new activity.
     */
    fun startActivity(intent: Intent) = activity.startActivity(intent)

    /**
     * Navigates to a new screen specified by the [screen] parameter.
     * Optionally, it can also pop the current screen from the stack and configure
     * the navigation behavior with the [starter] and [popScreen].
     *
     * @param screen The screen to navigate to, implementing [VROScreen].
     * @param starter An optional [VRONavStarter] to configure the navigation.
     * @param popScreen An optional screen to pop from the back stack before navigating.
     * @param inclusive If true, the [popScreen] will also be removed from the back stack.
     */
    fun navigateToScreen(
        screen: VROScreen<*, *>,
        starter: VRONavStarter? = null,
        popScreen: VROScreen<*, *>? = null,
        inclusive: Boolean = false,
    ) {
        navigateToRoute(
            destinationRoute = screen.destinationRoute(),
            starter = starter,
            popScreen = popScreen,
            inclusive = inclusive
        )
    }

    /**
     * Opens a bottom sheet defined by the [bottomSheet] parameter.
     * Optionally, the navigation behavior can be customized with [starter], [popScreen], and [inclusive].
     *
     * @param bottomSheet The bottom sheet content to be displayed, either a ViewModel-based or a regular Composable.
     * @param starter An optional [VRONavStarter] to configure the navigation.
     * @param popScreen An optional screen to pop from the back stack before opening the bottom sheet.
     * @param inclusive If true, the [popScreen] will also be removed from the back stack.
     */
    fun openBottomSheet(
        bottomSheet: VROComposableViewModelBottomSheetContent<*, *>,
        starter: VRONavStarter? = null,
        popScreen: VROScreen<*, *>? = null,
        inclusive: Boolean = false,
    ) {
        navigateToRoute(
            destinationRoute = bottomSheet.destinationRoute(),
            starter = starter,
            popScreen = popScreen,
            inclusive = inclusive
        )
    }

    /**
     * Opens a bottom sheet defined by the [bottomSheet] parameter.
     * Optionally, the navigation behavior can be customized with [starter], [popScreen], and [inclusive].
     *
     * @param bottomSheet The bottom sheet content to be displayed.
     * @param starter An optional [VRONavStarter] to configure the navigation.
     * @param popScreen An optional screen to pop from the back stack before opening the bottom sheet.
     * @param inclusive If true, the [popScreen] will also be removed from the back stack.
     */
    fun openBottomSheet(
        bottomSheet: VROComposableBottomSheetContent<*>,
        starter: VRONavStarter? = null,
        popScreen: VROScreen<*, *>? = null,
        inclusive: Boolean = false,
    ) {
        navigateToRoute(
            destinationRoute = bottomSheet.destinationRoute(),
            starter = starter,
            popScreen = popScreen,
            inclusive = inclusive
        )
    }

    /**
     * Helper function that performs navigation to the provided destination route.
     *
     * @param destinationRoute The destination route to navigate to.
     * @param starter An optional [VRONavStarter] to configure the navigation.
     * @param popScreen An optional screen to pop from the back stack before navigating.
     * @param inclusive If true, the [popScreen] will also be removed from the back stack.
     */
    private fun navigateToRoute(
        destinationRoute: String,
        starter: VRONavStarter? = null,
        popScreen: VROScreen<*, *>? = null,
        inclusive: Boolean = false,
    ) {
        navController.navigate(destinationRoute) {
            popScreen?.destinationRoute()?.let {
                popUpTo(it) {
                    this.inclusive = inclusive
                }
            }
        }
        starter?.let {
            putStarterParam(navController.currentDestination?.id.toString(), it)
        }
    }
}
