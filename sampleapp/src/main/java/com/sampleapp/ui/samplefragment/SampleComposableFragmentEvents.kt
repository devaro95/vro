package com.sampleapp.ui.samplefragment

import com.vro.event.VROEvent

sealed class SampleComposableFragmentEvents : VROEvent{
    data object Update : SampleComposableFragmentEvents()
    data object NavigateDestination : SampleComposableFragmentEvents()
}