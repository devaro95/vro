package com.vro.compose.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.skeleton.VROSkeleton
import com.vro.compose.states.*
import com.vro.compose.utils.isTablet
import com.vro.event.VROEvent
import com.vro.event.VROEventListener
import com.vro.navigation.VROBackResult
import com.vro.state.VRODialogData
import com.vro.state.VROState
import kotlinx.coroutines.flow.Flow

abstract class VROScreenBase<S : VROState, E : VROEvent> {

    open val skeleton: VROSkeleton? = null

    internal lateinit var eventListener: VROEventListener<E>

    open val tabletDesignEnabled: Boolean = false

    internal lateinit var screenState: S

    private val observableTopBarFlow = createTopBarSharedFlow()

    internal val topBarFlow: Flow<VROTopBarLaunchState> = observableTopBarFlow

    private val observableBottomBarFlow = createBottomBarSharedFlow()

    internal val bottomBarFlow: Flow<VROBottomBarLaunchState> = observableBottomBarFlow

    private var topBarState: VROTopBarState = VROTopBarState()

    private var bottomBarState: VROBottomBarState = VROBottomBarState()

    open fun setTopBar(): VROTopBarState? = null

    open fun setBottomBar(): VROBottomBarState? = null

    @Composable
    internal fun ComposableScreenSkeleton() {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            skeleton?.SkeletonContent()
        }
    }

    @Composable
    internal fun ComposableScreenContainer(state: S) {
        screenState = state
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (isTablet() && tabletDesignEnabled) {
                ScreenTabletContent(state)
            } else {
                ScreenContent(state)
            }
        }
    }

    @Composable
    abstract fun ScreenContent(state: S)

    @Composable
    open fun ScreenTabletContent(state: S) = Unit

    @VROLightMultiDevicePreview
    @Composable
    abstract fun ScreenPreview()

    @SuppressLint("ComposableNaming")
    @Composable
    open fun onDialog(data: VRODialogData) = Unit

    @SuppressLint("ComposableNaming")
    @Composable
    open fun onError(error: Throwable, data: Any?) = Unit

    open fun oneTimeHandler(id: Int, state: S) = Unit

    fun event(event: E) {
        eventListener.eventListener(event)
    }

    fun navigateBack(result: VROBackResult? = null) {
        eventListener.eventBack(result)
    }

    fun updateTopBar(changeStateFunction: VROTopBarState.() -> VROTopBarState) {
        observableTopBarFlow.tryEmit(
            VROTopBarLaunchState.Launch(
                state = changeStateFunction.invoke(setTopBar() ?: topBarState)
            )
        )
    }

    fun updateBottomBar(changeStateFunction: VROBottomBarState.() -> VROBottomBarState) {
        observableBottomBarFlow.tryEmit(
            VROBottomBarLaunchState.Launch(
                state = changeStateFunction.invoke(setBottomBar() ?: bottomBarState)
            )
        )
    }

    internal fun clearTopBarFlow() {
        observableTopBarFlow.tryEmit(VROTopBarLaunchState.Clear())
    }

    internal fun updateBottomBar() {
        observableBottomBarFlow.tryEmit(VROBottomBarLaunchState.Clear())
    }
}