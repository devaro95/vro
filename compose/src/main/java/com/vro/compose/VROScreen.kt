package com.vro.compose

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.vro.compose.extensions.VroComposablePreview
import com.vro.compose.extensions.VroComposableSectionContainer
import com.vro.compose.preview.GeneratePreview
import com.vro.compose.states.VROComposableScaffoldState
import com.vro.compose.utils.isTablet
import com.vro.event.VROEvent
import com.vro.navigation.VROBackResult
import com.vro.navigation.VRODestination
import com.vro.state.VRODialogState
import com.vro.state.VROState

abstract class VROScreen<S : VROState, D : VRODestination, E : VROEvent> {

    internal lateinit var viewModel: VROComposableViewModel<S, D>

    lateinit var eventLauncher: E

    lateinit var context: Context

    @Composable
    abstract fun ComposablePreview()

    @Composable
    fun CreatePreview(theme: VROComposableTheme? = null) {
        GeneratePreview(theme) {
            VroComposablePreview(composableContent())
        }
    }

    @Composable
    internal fun ComposableSectionContainer(state: S, eventListener: E) {
        VroComposableSectionContainer(
            state,
            eventListener,
            if (isTablet() && composableTabletContent().isNotEmpty()) {
                composableTabletContent()
            } else {
                composableContent()
            }
        )
    }

    @Composable
    abstract fun composableContent(): List<VROSection<S, E>>

    @Composable
    open fun composableTabletContent(): List<VROSection<S, E>> = emptyList()

    @Composable
    open fun ComposableSkeleton() = Unit

    @Composable
    open fun OnDialog(data: VRODialogState) = Unit

    fun navigateBack(result: VROBackResult? = null) {
        viewModel.navigateBack(result)
    }

    internal fun configureScaffold(
        scaffoldState: MutableState<VROComposableScaffoldState>,
        bottomBar: Boolean,
    ) {
        scaffoldState.value = VROComposableScaffoldState(
            topBarState = setTopBar(),
            showBottomBar = bottomBar
        )
    }

    open fun setTopBar(): VROComposableScaffoldState.VROTopBarState? = null
}
