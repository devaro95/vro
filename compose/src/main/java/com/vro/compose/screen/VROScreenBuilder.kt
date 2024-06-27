package com.vro.compose.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vro.compose.extensions.VroComposableScreenContainer
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.states.VROComposableScaffoldState.VROBottomBarState
import com.vro.compose.states.VROComposableScaffoldState.VROTopBarState
import com.vro.compose.utils.isTablet
import com.vro.event.VROEvent
import com.vro.event.VROEventListener
import com.vro.navigation.VROBackResult
import com.vro.state.VRODialogState
import com.vro.state.VROState

abstract class VROScreenBuilder<S : VROState, E : VROEvent> {

    private lateinit var eventListener: VROEventListener<E>

    open fun setTopBar(): VROTopBarState? = null

    open fun setBottomBar(): VROBottomBarState? = null

    @Composable
    open fun Modifier.setModifier(): Modifier = this

    open val tabletModeEnabled: Boolean = false

    @Composable
    internal fun ComposableContainer(state: S, eventListener: VROEventListener<E>) {
        this.eventListener = eventListener
        VroComposableScreenContainer(
            topBarState = setTopBar(),
            bottomBarState = setBottomBar(),
            content = {
                if (isTablet() && tabletModeEnabled) {
                    ScreenTabletContent(state)
                } else {
                    ScreenContent(state)
                }
            }
        )
    }

    @Composable
    abstract fun ScreenContent(state: S)

    @Composable
    open fun ScreenTabletContent(state: S) = Unit

    @VROLightMultiDevicePreview
    @Composable
    abstract fun ScreenPreview()

    @Composable
    open fun OnDialog(data: VRODialogState) = Unit

    fun event(event: E) {
        eventListener.eventListener(event)
    }

    fun navigateBack(result: VROBackResult? = null) {
        eventListener.eventBack(result)
    }
}