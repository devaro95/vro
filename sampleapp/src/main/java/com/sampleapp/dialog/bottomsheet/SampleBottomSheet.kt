package com.sampleapp.dialog.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vro.compose.components.VROBottomSheet
import com.vro.compose.listeners.VROBottomSheetListener

@ExperimentalMaterial3Api
@Composable
fun SampleBottomSheet(
    modifier: Modifier = Modifier,
    containerColor: Color = BottomSheetDefaults.ContainerColor,
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    height: Dp = LocalConfiguration.current.screenHeightDp.dp,
    fullExpanded: Boolean,
    onDismissListener: VROBottomSheetListener,
) {
    VROBottomSheet<SampleBottomSheetEvents, SampleBottomSheetState, SampleBottomSheetViewModel>(
        modifier = modifier,
        height = height,
        onDismissListener = onDismissListener,
        shape = shape,
        containerColor = containerColor,
        fullExpanded = fullExpanded,
        viewModelClass = SampleBottomSheetViewModel()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = state.name,
                onValueChange = { eventLauncher.onNameChange(it) }
            )
        }
    }
}