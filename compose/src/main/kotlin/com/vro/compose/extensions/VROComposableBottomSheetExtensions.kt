package com.vro.compose.extensions

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.vro.compose.dialog.*
import com.vro.compose.dialog.VroComposableBottomSheetContent
import com.vro.compose.states.rememberSheetState
import com.vro.event.VROEvent
import com.vro.state.VROState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialNavigationApi::class)
fun <VM : VROComposableDialogViewModel<S, E>, S : VROState, E : VROEvent> NavGraphBuilder.vroBottomSheet(
    viewModel: @Composable () -> VM,
    content: VROComposableBottomSheetContent<S, E>,
    initialState: S? = null,
    onDismiss: () -> Unit = { },
) {
    bottomSheet(
        route = content.destinationRoute(),
    ) {
        VroComposableBottomSheetContent(
            viewModel = viewModel.invoke(),
            content = content,
            initialState = initialState,
            onDismiss = onDismiss
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <VM : VROComposableDialogViewModel<S, E>, S : VROState, E : VROEvent> VroBottomSheet(
    modifier: Modifier = Modifier,
    viewModel: @Composable () -> VM,
    content: VROComposableBottomSheetContent<S, E>,
    initialState: S? = null,
    onDismiss: () -> Unit,
    fullExpanded: Boolean = false,
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    containerColor: Color = Color.White,
) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = fullExpanded)
    val sheetStateVro = rememberSheetState()
    sheetStateVro.setHideActionListener {
        coroutineScope.launch {
            sheetState.hide()
            onDismiss()
        }
    }
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = shape,
        modifier = Modifier
            .then(modifier)
            .statusBarsPadding(),
        containerColor = containerColor
    ) {
        VroComposableBottomSheetContent(
            viewModel = viewModel.invoke(),
            content = content,
            initialState = initialState,
            onDismiss = onDismiss,
        )
    }
}