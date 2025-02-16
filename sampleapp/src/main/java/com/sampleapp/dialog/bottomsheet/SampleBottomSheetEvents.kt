package com.sampleapp.dialog.bottomsheet

import com.vro.event.VROEvent

sealed class SampleBottomSheetEvents : VROEvent {
    data object OnButton : SampleBottomSheetEvents()
    data object Dismiss : SampleBottomSheetEvents()
    data object OnDialog : SampleBottomSheetEvents()
}