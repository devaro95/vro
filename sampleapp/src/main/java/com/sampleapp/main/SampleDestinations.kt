package com.sampleapp.main

import com.vro.navigation.VRODestination

sealed class SampleDestinations : VRODestination() {
    data object Home : SampleDestinations()
    data object Profile : SampleDestinations()
    data object BottomSheet : SampleDestinations()
}