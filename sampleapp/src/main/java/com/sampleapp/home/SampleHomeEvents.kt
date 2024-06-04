package com.sampleapp.home

import com.vro.event.VROEvent

sealed class SampleHomeEvents : VROEvent {
    data object ProfileNavigation : SampleHomeEvents()
    data object HideDialog : SampleHomeEvents()
    data object UpdateTextClick : SampleHomeEvents()
    data object ShowNavigationBottomSheetClick : SampleHomeEvents()
    data object ShowBottomSheetClick : SampleHomeEvents()
    data object ShowSimpleDialogClick : SampleHomeEvents()
    data object ShowSimpleDialogWithViewModelClick : SampleHomeEvents()
    data object BottomSheetDismiss : SampleHomeEvents()
    data object DetailNavigation : SampleHomeEvents()
    data object VmDialogDismiss : SampleHomeEvents()
    data object ActivityFragment : SampleHomeEvents()
}