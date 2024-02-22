package com.sampleapp.detail

import com.vro.event.VROEvent

interface SampleDetailEvent : VROEvent {
    fun onActionProfileNavigationClick()
}