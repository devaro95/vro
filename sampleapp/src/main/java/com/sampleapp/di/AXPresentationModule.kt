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
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.module

val axPresentationModule = module {
    factoryOf(::SampleFragmentViewModel)
    factoryOf(::SampleFragmentViewModel)
    factoryOf(::SampleHomeViewModel)
    factoryOf(::SampleProfileViewModel)
    factoryOf(::SampleDetailViewModel)
    factoryOf(::SampleBottomSheetNavViewModel)
    factoryOf(::SampleBottomSheetViewModel)
    factoryOf(::SampleVMDialogViewModel)

    scope<SampleComposableFragment> {
        scopedOf(::SampleComposableFragmentNavigator)
        factoryOf(::SampleComposableFragmentViewModel)
    }

    scope<SampleComposableDestinationFragment> {
        scopedOf(::SampleComposableDestinationFragmentNavigator)
        factoryOf(::SampleComposableDestinationFragmentViewModel)
    }
}