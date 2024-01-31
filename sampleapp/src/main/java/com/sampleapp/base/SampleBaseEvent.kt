package com.sampleapp.base

import com.vro.event.VROEvent

sealed class SampleBaseEvent : VROEvent {
    data object LoadingClose : SampleBaseEvent()
}