package com.sampleapp.profile

import com.sampleapp.main.Destinations
import com.sampleapp.profile.ProfileNavParam.Initialize
import com.vro.fragment.VROViewModel
import com.vro.navparam.VRONavParam

class ProfileViewModel : VROViewModel<ProfileState, Destinations, ProfileEvent>() {

    override val initialViewState: ProfileState = ProfileState.INITIAL

    override fun eventListener(event: ProfileEvent) = Unit

    override fun onNavParam(navParam: VRONavParam?) {
        when (navParam) {
            is Initialize -> updateDataState { copy(userId = navParam.userId) }
        }
    }
}