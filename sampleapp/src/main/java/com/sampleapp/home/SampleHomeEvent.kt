package com.sampleapp.home

import com.vro.event.VROEvent

interface SampleHomeEvent : VROEvent {
    fun onActionButtonClick()
    fun navigateToProfile()
    fun onActionHideDialog()
}