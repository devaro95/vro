package com.sampleapp.ui.home

import androidx.compose.runtime.Composable
import com.sampleapp.ui.base.SampleBaseScreen
import com.sampleapp.dialog.bottomsheet.SampleBottomSheet
import com.sampleapp.dialog.bottomsheet.SampleBottomSheetViewModel
import com.sampleapp.dialog.withviewmodel.SampleVMDialog
import com.sampleapp.dialog.withviewmodel.SampleVMDialogViewModel
import com.sampleapp.ui.home.SampleHomeViewModel.Companion.BOTTOM_SHEET
import com.sampleapp.ui.home.SampleHomeViewModel.Companion.SIMPLE_VIEW_MODEL_DIALOG
import com.sampleapp.ui.home.sections.BottomButtonSection
import com.sampleapp.ui.home.sections.TopButtonSection
import com.sampleapp.topbar.sampleHomeToolbar
import com.vro.compose.extensions.VROComposableDialog
import com.vro.compose.extensions.VroBottomSheet
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.state.VRODialogState
import org.koin.androidx.compose.koinViewModel

class SampleHomeScreen : SampleBaseScreen<SampleHomeState, SampleHomeEvents>() {

    override fun setTopBar() =
        sampleHomeToolbar(
            context = context,
            onAction = { event(SampleHomeEvents.Profile) }
        )

    @Composable
    override fun screenSections() =
        listOf(
            TopButtonSection(),
            BottomButtonSection()
        )

    @Composable
    override fun screenTabletSections() =
        listOf(
            TopButtonSection(),
            BottomButtonSection()
        )

    @Composable
    @VROLightMultiDevicePreview
    override fun ScreenPreview() {
        CreatePreview()
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

            else -> super.OnDialog(data)
        }
    }
}