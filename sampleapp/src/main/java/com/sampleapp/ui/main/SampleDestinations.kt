package com.sampleapp.ui.main

import com.sampleapp.ui.detail.SampleDetailState
import com.vro.navigation.VRODestination

sealed class SampleDestinations : VRODestination() {
    data object HomeNavigation : SampleDestinations()
    data object ProfileNavigation : SampleDestinations()
    data class DetailNavigation(val state: SampleDetailState) : SampleDestinations()
    data object BottomSheetNavigation : SampleDestinations()
    data object ActivityFragmentNavigation : SampleDestinations()
    data object TemplateNavigation : SampleDestinations()
}