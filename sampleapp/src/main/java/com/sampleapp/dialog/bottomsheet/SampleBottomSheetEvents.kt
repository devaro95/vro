package com.sampleapp.dialog.bottomsheet

import com.vro.event.VROEvent

sealed class SampleBottomSheetEvents : VROEvent {
    data object OnButton : SampleBottomSheetEvents()
}