package com.sampleapp.detail

import com.sampleapp.base.SampleBaseViewModel
import com.sampleapp.main.SampleDestinations

class SampleDetailViewModel : SampleBaseViewModel<SampleDetailState, SampleDestinations>(), SampleDetailEvent {

    override val initialState: SampleDetailState = SampleDetailState.INITIAL

    override fun onActionProfileNavigationClick() {
        navigate(SampleDestinations.Profile)
    }

}