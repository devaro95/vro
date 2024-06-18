package com.sampleapp.ui.detail

import com.vro.event.VROEvent

sealed class SampleDetailEvents : VROEvent {
    data object Profile : SampleDetailEvents()
}