package com.sampleapp.home

import com.sampleapp.ui.base.SampleBaseViewModel.Companion.DIALOG_SIMPLE
import com.sampleapp.ui.home.*
import com.sampleapp.ui.home.SampleHomeEvents.ActivityFragment
import com.sampleapp.ui.home.SampleHomeEvents.BottomSheetDismiss
import com.sampleapp.ui.home.SampleHomeEvents.Detail
import com.sampleapp.ui.home.SampleHomeEvents.HideDialog
import com.sampleapp.ui.home.SampleHomeEvents.Profile
import com.sampleapp.ui.home.SampleHomeEvents.ShowBottomSheet
import com.sampleapp.ui.home.SampleHomeEvents.ShowNavigationBottomSheet
import com.sampleapp.ui.home.SampleHomeEvents.ShowSimpleDialog
import com.sampleapp.ui.home.SampleHomeEvents.ShowSimpleDialogWithViewModel
import com.sampleapp.ui.home.SampleHomeEvents.UpdateText
import com.sampleapp.ui.home.SampleHomeEvents.VmDialogDismiss
import com.sampleapp.ui.home.SampleHomeViewModel.Companion.BOTTOM_SHEET
import com.sampleapp.ui.home.SampleHomeViewModel.Companion.FIRST_TEXT
import com.sampleapp.ui.home.SampleHomeViewModel.Companion.SIMPLE_VIEW_MODEL_DIALOG
import com.sampleapp.ui.main.SampleDestinations.ActivityFragmentNavigation
import com.sampleapp.ui.main.SampleDestinations.BottomSheetNavigation
import com.sampleapp.ui.main.SampleDestinations.DetailNavigation
import com.sampleapp.ui.main.SampleDestinations.ProfileNavigation
import com.vro.compose.testing.VROComposableViewModelTest
import junit.framework.TestCase.assertEquals
import org.junit.Test

class SampleHomeViewModelTest : VROComposableViewModelTest<SampleHomeState, SampleHomeViewModel, SampleHomeEvents>() {

    override fun onSetupInitialState() = SampleHomeState.INITIAL

    override fun onSetupViewModel() = SampleHomeViewModel()

    @Test
    fun `onStart should update screen state`() {
        onStart()
        getDataState().also {
            assertEquals(FIRST_TEXT, it.text)
        }
    }

    @Test
    fun `BottomSheetDismiss Event should updateScreen`() {
        event(BottomSheetDismiss)
        verifyUpdateScreen()
    }

    @Test
    fun `DetailNavigation Event should navigate to DetailNavigation`() {
        event(Detail)
        verifyNavigation(DetailNavigation)
    }

    @Test
    fun `HideDialog Event should updateScreen`() {
        event(HideDialog)
        verifyUpdateScreen()
    }

    @Test
    fun `Profile Event should navigate to profile`() {
        event(Profile)
        verifyNavigation(ProfileNavigation)
    }

    @Test
    fun `ShowNavigationBottomSheet Event should navigate to BottomSheetNavigation`() {
        event(ShowNavigationBottomSheet)
        verifyNavigation(BottomSheetNavigation)
    }

    @Test
    fun `ShowBottomSheet Event should open dialog`() {
        event(ShowBottomSheet)
        verifyDialog(BOTTOM_SHEET)
    }

    @Test
    fun `ShowSimpleDialog Event should open dialog`() {
        event(ShowSimpleDialog)
        verifyDialog(DIALOG_SIMPLE)
    }

    @Test
    fun `ShowSimpleDialogWithViewModel Event should open dialog`() {
        event(ShowSimpleDialogWithViewModel)
        verifyDialog(SIMPLE_VIEW_MODEL_DIALOG)
    }

    @Test
    fun `UpdateText Event should update screen state`() {
        event(UpdateText)
        getDataState().also {
            assertEquals(FIRST_TEXT, it.text)
        }
    }

    @Test
    fun `VmDialogDismiss Event should update screen`() {
        event(VmDialogDismiss)
        verifyUpdateScreen()
    }

    @Test
    fun `ActivityFragment Event should navigate to ActivityFragmentNavigation`() {
        event(ActivityFragment)
        verifyNavigation(ActivityFragmentNavigation)
    }
}