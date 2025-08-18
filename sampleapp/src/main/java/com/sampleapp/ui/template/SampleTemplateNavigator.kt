package com.sampleapp.ui.template

import com.vro.compose.navigator.VROTemplateNavigator
import com.vro.navigation.VRODestination

class SampleTemplateNavigator : VROTemplateNavigator<SampleTemplateDestinations>() {
    override fun navigate(destination: SampleTemplateDestinations) {

    }
}

sealed class SampleTemplateDestinations : VRODestination()