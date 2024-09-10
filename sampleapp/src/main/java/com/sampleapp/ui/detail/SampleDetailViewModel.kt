package com.sampleapp.ui.detail

import com.sampleapp.ui.base.SampleBaseViewModel
import com.sampleapp.ui.detail.SampleDetailEvents.Profile
import com.sampleapp.ui.main.SampleDestinations
import com.vro.navstarter.VRONavStarter

class SampleDetailViewModel : SampleBaseViewModel<SampleDetailState, SampleDestinations, SampleDetailEvents>() {

    override val initialState: SampleDetailState = SampleDetailState.INITIAL

    override fun onNavParam(navParam: VRONavStarter?) {
        when (navParam) {
            is SampleDetailStarter.Initial -> updateScreen { navParam.state }
        }
    }

    override fun eventListener(event: SampleDetailEvents) {
        when (event) {
            Profile -> onActionProfileNavigationClick()
        }
    }

    private fun onActionProfileNavigationClick() {
        navigate(SampleDestinations.ProfileNavigation)
    }
}