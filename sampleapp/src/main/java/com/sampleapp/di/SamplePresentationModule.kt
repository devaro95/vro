package com.sampleapp.di

import com.sampleapp.dialog.bottomsheet.SampleBottomSheetNavViewModel
import com.sampleapp.dialog.bottomsheet.SampleBottomSheetViewModel
import com.sampleapp.dialog.withviewmodel.SampleVMDialogViewModel
import com.sampleapp.ui.detail.SampleDetailViewModel
import com.sampleapp.ui.fragmentactivity.SampleFragmentViewModel
import com.sampleapp.ui.home.SampleHomeViewModel
import com.sampleapp.ui.profile.SampleProfileViewModel
import com.sampleapp.ui.samplefragment.*
import com.sampleapp.ui.samplefragment.destination.*
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::SampleFragmentViewModel)
    viewModelOf(::SampleFragmentViewModel)
    viewModelOf(::SampleHomeViewModel)
    viewModelOf(::SampleProfileViewModel)
    viewModelOf(::SampleDetailViewModel)
    viewModelOf(::SampleBottomSheetNavViewModel)
    viewModelOf(::SampleBottomSheetViewModel)
    viewModelOf(::SampleVMDialogViewModel)

    scope<SampleComposableFragment> {
        scopedOf(::SampleComposableFragmentNavigator)
        viewModelOf(::SampleComposableFragmentViewModel)
    }

    scope<SampleComposableDestinationFragment> {
        scopedOf(::SampleComposableDestinationFragmentNavigator)
        viewModelOf(::SampleComposableDestinationFragmentViewModel)
    }
}