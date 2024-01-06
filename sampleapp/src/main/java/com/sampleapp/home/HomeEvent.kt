package com.sampleapp.home

import com.vro.event.VROEvent

sealed class HomeEvent : VROEvent {
    data object ButtonClick : HomeEvent()
}