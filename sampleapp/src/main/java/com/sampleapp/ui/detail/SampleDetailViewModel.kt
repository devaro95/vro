package com.sampleapp.ui.detail

import com.sampleapp.ui.base.SampleBaseViewModel
import com.sampleapp.ui.detail.SampleDetailEvents.Profile
import com.sampleapp.ui.main.SampleDestinations

class SampleDetailViewModel : SampleBaseViewModel<SampleDetailState, SampleDestinations, SampleDetailEvents>() {

    override val initialState: SampleDetailState = SampleDetailState.INITIAL

    override fun eventListener(event: SampleDetailEvents) {
        when (event) {
            Profile -> onActionProfileNavigationClick()
        }
    }

    private fun onActionProfileNavigationClick() {
        navigate(SampleDestinations.ProfileNavigation)
    }
}