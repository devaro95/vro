package com.vro.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vro.compose.preview.VROMultiDevicePreview
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VRODialogState
import com.vro.state.VROState
import java.io.Serializable

abstract class VROComposableScreenContent<
        S : VROState,
        D : VRODestination,
        E : VROEvent,
        > : VROComposableScreen<S, D, E>() {

    @Composable
    override fun AddComposableContent(state: S) {
        ComposableContent(state)
    }

    @Composable
    override fun AddComposableDialog(dialogState: VRODialogState) {
        OnDialog(dialogState)
    }

    @Composable
    override fun AddComposableSkeleton() {
        Column(Modifier.fillMaxSize()) {
            ComposableSkeleton()
        }
    }

    @VROMultiDevicePreview
    @Composable
    abstract fun ComposablePreview()

    @Composable
    abstract fun ComposableContent(state: S)

    @Composable
    open fun ComposableSkeleton() = Unit

    @Composable
    open fun OnDialog(data: VRODialogState) = Unit

    fun navigateBack(result: Serializable? = null) {
        viewModel.navigateBack(result)
    }
}
