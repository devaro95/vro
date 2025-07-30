package com.sampleapp.detail

import com.sampleapp.ui.detail.*
import com.sampleapp.ui.detail.SampleDetailEvents.Profile
import com.sampleapp.ui.main.SampleDestinations
import com.sampleapp.ui.main.SampleDestinations.ProfileNavigation
import com.vro.core_android.testing.VROViewModelTest
import org.junit.Test

class SampleDetailViewModelTest : VROViewModelTest<SampleDetailState, SampleDetailViewModel, SampleDetailEvents>() {

    override fun onSetupInitialState() = SampleDetailState.INITIAL

    override fun onSetupViewModel() = SampleDetailViewModel()

    @Test
    fun `ProfileNavigation Event should navigate to profile`() {
        event(Profile)
        verifyNavigation(ProfileNavigation)
    }
}