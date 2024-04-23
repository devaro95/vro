package com.sampleapp.detail

import com.vro.event.VROEvent

sealed class SampleDetailEvent : VROEvent {
    data object ProfileNavigation : SampleDetailEvent()
}