package com.sampleapp.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sampleapp.R
import com.sampleapp.dialog.bottomsheet.SampleBottomSheet
import com.sampleapp.dialog.bottomsheet.SampleBottomSheetViewModel
import com.sampleapp.dialog.withviewmodel.SampleVMDialog
import com.sampleapp.dialog.withviewmodel.SampleVMDialogViewModel
import com.sampleapp.ui.base.SampleBaseScreen
import com.sampleapp.ui.home.SampleHomeViewModel.Companion.BOTTOM_SHEET
import com.sampleapp.ui.home.SampleHomeViewModel.Companion.SIMPLE_VIEW_MODEL_DIALOG
import com.sampleapp.ui.home.SampleHomeViewModel.Companion.SNACK_BAR
import com.vro.compose.extensions.VROComposableDialog
import com.vro.compose.extensions.VroBottomSheet
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.skeleton.VROSkeleton
import com.vro.compose.states.VROBottomBarState
import com.vro.compose.states.VROTopBarState
import com.vro.compose.utils.vroVerticalScroll
import com.vro.constants.INT_ZERO
import com.vro.state.VRODialogState
import org.koin.androidx.compose.koinViewModel

class SampleHomeScreen(
    override val skeleton: VROSkeleton = SampleHomeSkeleton(),
) : SampleBaseScreen<SampleHomeState, SampleHomeEvents>() {

    override fun setBottomBar() = VROBottomBarState(selectedItem = INT_ZERO)

    override fun setTopBar(): VROTopBarState = VROTopBarState(
        title = "Sample Home",
        navigationButton = VROTopBarState.VROTopBarButton(
            icon = R.drawable.ic_profile,
            onClick = { event(SampleHomeEvents.Profile) },
        )
    )

    @Composable
    override fun ScreenContent(state: SampleHomeState) {
        Column(
            modifier = Modifier
                .vroVerticalScroll()
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Top Button Section")
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleHomeEvents.UpdateText) }
            ) {
                Text(text = state.text)
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleHomeEvents.ShowNavigationBottomSheet) }
            ) {
                Text(text = "Show Navigation Bottom Sheet")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleHomeEvents.ShowBottomSheet) }
            ) {
                Text(text = "Show Bottom Sheet")
            }
            Text(text = "Bottom Button Section")
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleHomeEvents.ShowSimpleDialog) }
            ) {
                Text(text = "Show Dialog Example")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleHomeEvents.ShowSimpleDialogWithViewModel) }
            ) {
                Text(text = "Show Dialog VM Example")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleHomeEvents.ShowSnackBar) }
            ) {
                Text(text = "Show SnackBar Example")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleHomeEvents.Profile) }
            ) {
                Text(text = "Profile Navigation")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleHomeEvents.Detail) }
            ) {
                Text(text = "Detail Navigation")
            }
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { event(SampleHomeEvents.ActivityFragment) }
            ) {
                Text(text = "Activity Fragment")
            }
        }
    }

    @Composable
    @VROLightMultiDevicePreview
    override fun ScreenPreview() {
        ScreenContent(state = SampleHomeState.INITIAL)
    }

    @Composable
    override fun OnDialog(data: VRODialogState) {
        when (data.type) {
            SIMPLE_VIEW_MODEL_DIALOG -> VROComposableDialog(
                viewModel = { koinViewModel<SampleVMDialogViewModel>() },
                content = SampleVMDialog(),
                onDismiss = { event(SampleHomeEvents.VmDialogDismiss) },
            )

            BOTTOM_SHEET -> VroBottomSheet(
                viewModel = { koinViewModel<SampleBottomSheetViewModel>() },
                content = SampleBottomSheet(),
                onDismiss = { event(SampleHomeEvents.BottomSheetDismiss) },
                fullExpanded = true
            )

            SNACK_BAR -> VROSnackBar(message = "This a Snack Bar Example")

            else -> super.OnDialog(data)
        }
    }
}