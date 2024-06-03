package com.sampleapp.dialog.withviewmodel

import com.vro.event.VROEvent

sealed class SampleVMDialogEvents : VROEvent {
    data object TextChange : SampleVMDialogEvents()
}