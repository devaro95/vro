package com.sampleapp.dialog.bottomsheet

import com.vro.event.VROEvent

interface SampleBottomSheetEvents : VROEvent {
    fun onNameChange(name: String)
}