package com.sampleapp.profile

import com.sampleapp.ui.profile.*
import com.vro.compose.testing.VROComposableViewModelTest
import junit.framework.TestCase.assertEquals
import org.junit.Test

class SampleProfileViewModelTest : VROComposableViewModelTest<SampleProfileState, SampleProfileViewModel, SampleProfileEvents>() {

    override fun onSetupInitialState() = SampleProfileState.INITIAL

    override fun onSetupViewModel() = SampleProfileViewModel()

    private val textMock = "testMock"

    @Test
    fun `onNavParam should update screen state`() {
        onNavParam(SampleProfileNavStarter.Initialize(userId = textMock))
        getDataState().also {
            assertEquals(textMock, it.userId)
        }
    }
}