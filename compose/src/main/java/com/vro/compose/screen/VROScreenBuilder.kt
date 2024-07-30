package com.vro.compose.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.skeleton.VROSkeleton
import com.vro.compose.utils.isTablet
import com.vro.event.VROEvent
import com.vro.event.VROEventListener
import com.vro.navigation.VROBackResult
import com.vro.state.VRODialogState
import com.vro.state.VROState

abstract class VROScreenBuilder<S : VROState, E : VROEvent>{

    open val skeleton: VROSkeleton? = null

    internal lateinit var eventListener: VROEventListener<E>

    open val tabletDesignEnabled: Boolean = false

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
        Column(
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

    @Composable
    open fun OnDialog(data: VRODialogState) = Unit

    fun event(event: E) {
        eventListener.eventListener(event)
    }

    fun navigateBack(result: VROBackResult? = null) {
        eventListener.eventBack(result)
    }
}