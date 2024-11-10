package com.sampleapp.ui.profile

import com.sampleapp.ui.base.SampleBaseViewModel
import com.sampleapp.ui.main.SampleDestinations
import com.sampleapp.ui.profile.SampleProfileNavStarter.Initialize
import com.vro.navstarter.VRONavStarter

class SampleProfileViewModel : SampleBaseViewModel<SampleProfileState, SampleDestinations, SampleProfileEvents>() {

    override val initialState: SampleProfileState = SampleProfileState.INITIAL

    override fun onEvent(event: SampleProfileEvents) = Unit

    override fun onNavParam(navParam: VRONavStarter?) {
        when (navParam) {
            is Initialize -> updateScreen { copy(userId = navParam.userId) }
        }
    }
}