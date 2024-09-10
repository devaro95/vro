package com.sampleapp.ui.samplefragment.destination

import com.vro.event.VROEvent

sealed class SampleComposableDestinationFragmentEvents : VROEvent {
    data object Update : SampleComposableDestinationFragmentEvents()
    data object BackWithResult : SampleComposableDestinationFragmentEvents()
}