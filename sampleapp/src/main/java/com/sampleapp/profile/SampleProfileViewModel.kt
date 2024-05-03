package com.sampleapp.profile

import com.sampleapp.base.SampleBaseViewModel
import com.sampleapp.main.SampleDestinations
import com.sampleapp.profile.SampleProfileNavStarter.Initialize
import com.vro.navstarter.VRONavStarter

class SampleProfileViewModel : SampleBaseViewModel<SampleProfileState, SampleDestinations, SampleProfileEvent>() {

    override val initialState: SampleProfileState = SampleProfileState.INITIAL

    override fun eventListener(event: SampleProfileEvent) = Unit

    override fun onNavParam(navParam: VRONavStarter?) {
        when (navParam) {
            is Initialize -> updateScreen { copy(userId = navParam.userId) }
        }
    }
}