/**
 * Package containing top app bar components.
 */
package com.vro.compose.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.states.VROTopBarBaseState.VROTopBarState

/**
 * Preview composable for the [VroTopBar] component.
 * Demonstrates a basic top bar configuration with a title.
 *
 * @see VroTopBar For the actual implementation
 */
@Composable
@VROLightMultiDevicePreview
fun VroTopBarPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        VroTopBar(
            state = VROTopBarState(
                title = {
                    Text(
                        text = "Title",
                        color = Color.Blue
                    )
                }
            )
        )
    }
}

/**
 * A customizable top app bar component that implements Material Design 3 specifications.
 *
 * Features:
 * - Centered title layout
 * - Configurable background color
 * - Custom height support
 * - Navigation and action buttons
 * - Full Material 3 theming support
 *
 * @param modifier Modifier for additional styling
 * @param state Configuration state containing:
 *   - Title composable
 *   - Background color
 *   - Height
 *   - Navigation button
 *   - Action buttons
 *
 * @sample VroTopBarPreview
 * @see VROTopBarState For state configuration options
 * @see CenterAlignedTopAppBar For underlying implementation
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VroTopBar(
    modifier: Modifier = Modifier,
    state: VROTopBarState,
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = state.background ?: MaterialTheme.colorScheme.background
        ),
        title = {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
            ) {
                state.title?.invoke()
            }
        },
        actions = { state.actionButton?.invoke(this) },
        navigationIcon = { state.navigationButton?.invoke() },
        modifier = modifier.setHeight(state.height)
    )
}

/**
 * Extension function that conditionally applies height modifier.
 *
 * @param height Optional height value in Dp
 * @return Original modifier if height is null, or modified version with height applied
 *
 * @see Modifier.height For base height implementation
 */
fun Modifier.setHeight(height: Dp?): Modifier {
    return height?.let { height(it) } ?: this
}