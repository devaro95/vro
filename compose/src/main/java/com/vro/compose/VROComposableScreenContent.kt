package com.vro.compose

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.vro.compose.preview.VROMultiDevicePreview
import com.vro.compose.states.VROComposableScaffoldState
import com.vro.event.VROEvent
import com.vro.navigation.VROBackResult
import com.vro.navigation.VRODestination
import com.vro.state.VRODialogState
import com.vro.state.VROState
import java.io.Serializable

abstract class VROComposableScreenContent<S : VROState, D : VRODestination, E : VROEvent> {

    internal lateinit var viewModel: VROComposableViewModel<S, D>

    lateinit var eventLauncher: E

    lateinit var context: Context

    @VROMultiDevicePreview
    @Composable
    abstract fun ComposablePreview()

    @Composable
    abstract fun ComposableContent(state: S)

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
