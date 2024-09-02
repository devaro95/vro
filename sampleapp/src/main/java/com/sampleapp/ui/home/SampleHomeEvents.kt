package com.sampleapp.ui.home

import com.vro.event.VROEvent

sealed class SampleHomeEvents : VROEvent {
    data object Profile : SampleHomeEvents()
    data object HideDialog : SampleHomeEvents()
    data object UpdateText : SampleHomeEvents()
    data object ShowNavigationBottomSheet : SampleHomeEvents()
    data object ShowBottomSheet : SampleHomeEvents()
    data object ShowSimpleDialog : SampleHomeEvents()
    data object ShowSimpleDialogWithViewModel : SampleHomeEvents()
    data object BottomSheetDismiss : SampleHomeEvents()
    data object Detail : SampleHomeEvents()
    data object VmDialogDismiss : SampleHomeEvents()
    data object ActivityFragment : SampleHomeEvents()
    data object OneTimeLaunch : SampleHomeEvents()
}