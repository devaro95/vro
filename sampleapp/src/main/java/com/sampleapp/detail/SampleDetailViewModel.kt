package com.sampleapp.detail

import com.sampleapp.base.SampleBaseViewModel
import com.sampleapp.main.SampleDestinations

class SampleDetailViewModel : SampleBaseViewModel<SampleDetailState, SampleDestinations>() {

    override val initialState: SampleDetailState = SampleDetailState.INITIAL

}