package com.sampleapp.home

import com.vro.event.VROEvent

interface SampleHomeEvents : VROEvent {
    fun navigateToProfile()
    fun onActionHideDialog()
    fun onActionUpdateTextClick()
    fun onActionShowBottomSheetClick()
    fun onActionBottomSheetDismiss()
}