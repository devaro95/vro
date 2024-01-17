package com.sampleapp.profile

import com.sampleapp.base.SampleBaseViewModel
import com.sampleapp.main.SampleDestinations
import com.sampleapp.profile.SampleProfileNavParam.Initialize
import com.vro.navparam.VRONavParam

class SampleProfileViewModel : SampleBaseViewModel<SampleProfileState, SampleDestinations, SampleProfileEvent>() {

    override val initialViewState: SampleProfileState = SampleProfileState.INITIAL

    override fun eventListener(event: SampleProfileEvent) = Unit

    override fun onNavParam(navParam: VRONavParam?) {
        when (navParam) {
            is Initialize -> updateDataState { copy(userId = navParam.userId) }
        }
    }
}