package com.sampleapp.home

import com.vro.event.VROEvent

sealed class SampleHomeEvent : VROEvent {
    data object ButtonClick : SampleHomeEvent()
    data object ProfileNavigation : SampleHomeEvent()
    data object HomeNavigation : SampleHomeEvent()
}