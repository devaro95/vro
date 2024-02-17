package com.vro.compose.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.vro.compose.VROComposableViewModel
import com.vro.compose.extensions.createViewModel
import com.vro.compose.listeners.VROBottomSheetListener
import com.vro.compose.states.VROSheetState
import com.vro.compose.states.rememberVROSheetState
import com.vro.event.VROEvent
import com.vro.navigation.VROEmptyDestination
import com.vro.state.VROState
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun <E : VROEvent, S : VROState, VM : VROComposableViewModel<S, VROEmptyDestination>> VROBottomSheet(
    modifier: Modifier = Modifier,
    height: Dp,
    containerColor: Color = BottomSheetDefaults.ContainerColor,
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    fullExpanded: Boolean,
    onDismissListener: VROBottomSheetListener,
    viewModelClass: VM,
    content: @Composable VROBottomSheetState<S, E>.() -> Unit,
) {
    val viewModel = createViewModel(viewModelSeed = viewModelClass)
    val eventLauncher = viewModel as E
    val state by viewModel.stateHandler.screenState.collectAsState(remember { viewModel.initialState })
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = fullExpanded)
    val vroSheetState = rememberVROSheetState()
    vroSheetState.setHideActionListener {
        coroutineScope.launch {
            sheetState.hide()
            onDismissListener.onDismiss()
        }
    }
    ModalBottomSheet(
        onDismissRequest = { onDismissListener.onDismiss() },
        sheetState = sheetState,
        shape = shape,
        modifier = Modifier
            .height(height)
            .then(modifier),
        containerColor = containerColor
    ) {
        content.invoke(VROBottomSheetState(vroSheetState, state, eventLauncher))
    }
}

data class VROBottomSheetState<S : VROState, E : VROEvent>(
    val vroSheetState: VROSheetState,
    val state: S,
    val eventLauncher: E,
) {
    fun hide() = vroSheetState.hide()
}
