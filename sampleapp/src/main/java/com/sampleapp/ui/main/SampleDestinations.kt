package com.sampleapp.ui.main

import com.vro.navigation.VRODestination

sealed class SampleDestinations : VRODestination() {
    data object HomeNavigation : SampleDestinations()
    data object ProfileNavigation : SampleDestinations()
    data object DetailNavigation : SampleDestinations()
    data object BottomSheetNavigation : SampleDestinations()
    data object ActivityFragmentNavigation : SampleDestinations()
}