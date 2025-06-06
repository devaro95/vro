package com.sampleapp.ui.home.handler

import androidx.compose.runtime.Composable
import com.sampleapp.dialog.bottomsheet.SampleBottomSheet
import com.sampleapp.dialog.bottomsheet.SampleBottomSheetViewModel
import com.sampleapp.dialog.withviewmodel.SampleVMDialog
import com.sampleapp.dialog.withviewmodel.SampleVMDialogListener
import com.sampleapp.dialog.withviewmodel.SampleVMDialogViewModel
import com.sampleapp.ui.base.SampleBaseDialogHandler
import com.sampleapp.ui.home.SampleHomeEvents
import com.sampleapp.ui.home.SampleHomeViewModel
import com.vro.compose.extensions.VROComposableDialog
import com.vro.compose.extensions.VroBottomSheet
import com.vro.state.VRODialogData
import org.koin.androidx.compose.koinViewModel

class SampleHomeDialogHandler : SampleBaseDialogHandler<SampleHomeEvents>() {

    @Composable
    override fun OnDialog(data: VRODialogData) {
        when (data.type) {
            SampleHomeViewModel.Companion.SIMPLE_VIEW_MODEL_DIALOG -> VROComposableDialog(
                viewModel = { koinViewModel<SampleVMDialogViewModel>() },
                content = SampleVMDialog(),
                onDismiss = { event(SampleHomeEvents.VmDialogDismiss) },
                listener = object : SampleVMDialogListener {
                    override fun hideDialog() = event(SampleHomeEvents.VmDialogDismiss)
                }
            )

            SampleHomeViewModel.Companion.BOTTOM_SHEET -> VroBottomSheet(
                viewModel = { koinViewModel<SampleBottomSheetViewModel>() },
                content = SampleBottomSheet(),
                onDismiss = { event(SampleHomeEvents.BottomSheetDismiss) },
                fullExpanded = true
            )

            else -> super.OnDialog(data)
        }
    }
}