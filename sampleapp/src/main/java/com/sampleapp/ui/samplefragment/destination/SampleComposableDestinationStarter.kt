package com.sampleapp.ui.samplefragment.destination

import com.vro.navstarter.VRONavStarter

sealed class SampleComposableDestinationStarter : VRONavStarter {
    data class Initial(val state: SampleComposableDestinationFragmentState) : SampleComposableDestinationStarter()
}