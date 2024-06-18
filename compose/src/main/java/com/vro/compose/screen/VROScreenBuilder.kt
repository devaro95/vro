package com.vro.compose.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vro.compose.VROComposableTheme
import com.vro.compose.VROSection
import com.vro.compose.extensions.VroScreenComposablePreview
import com.vro.compose.extensions.VroComposableSectionContainer
import com.vro.compose.preview.GeneratePreview
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.utils.isTablet
import com.vro.event.VROEvent
import com.vro.event.VROEventListener
import com.vro.navigation.VROBackResult
import com.vro.state.VRODialogState
import com.vro.state.VROState

abstract class VROScreenBuilder<S : VROState, E : VROEvent> {

    private lateinit var eventListener: VROEventListener<E>

    @Composable
    open fun Modifier.setModifier(): Modifier = this

    @Composable
    internal fun ComposableSectionContainer(state: S, eventListener: VROEventListener<E>) {
        this.eventListener = eventListener
        VroComposableSectionContainer(
            modifier = Modifier.setModifier(),
            state = state,
            eventListener = eventListener,
            sectionList = if (isTablet() && screenTabletSections().isNotEmpty()) {
                screenTabletSections()
            } else {
                screenSections()
            }
        )
    }

    @Composable
    abstract fun screenSections(): List<VROSection<S, E>>

    @Composable
    open fun screenTabletSections(): List<VROSection<S, E>> = emptyList()

    @VROLightMultiDevicePreview
    @Composable
    abstract fun ScreenPreview()

    @Composable
    open fun OnDialog(data: VRODialogState) = Unit

    @Composable
    fun CreatePreview(theme: VROComposableTheme? = null) {
        GeneratePreview(theme) {
            VroScreenComposablePreview(
                modifier = Modifier.setModifier(),
                contentList = screenSections()
            )
        }
    }

    fun event(event: E) {
        eventListener.eventListener(event)
    }

    fun navigateBack(result: VROBackResult? = null) {
        eventListener.eventBack(result)
    }
}