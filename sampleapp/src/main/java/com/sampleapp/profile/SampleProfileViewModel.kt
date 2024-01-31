package com.sampleapp.profile

import com.sampleapp.base.SampleBaseViewModel
import com.sampleapp.main.SampleDestinations
import com.sampleapp.profile.SampleProfileNavParam.Initialize
import com.vro.navparam.VRONavParam

class SampleProfileViewModel : SampleBaseViewModel<SampleProfileState, SampleDestinations>() {

    override val initialState: SampleProfileState = SampleProfileState.INITIAL

    override fun onNavParam(navParam: VRONavParam?) {
        when (navParam) {
            is Initialize -> updateScreen { copy(userId = navParam.userId) }
        }
    }
}