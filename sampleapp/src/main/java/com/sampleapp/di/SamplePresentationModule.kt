package com.sampleapp.di

import com.sampleapp.detail.SampleDetailViewModel
import com.sampleapp.dialog.bottomsheet.SampleBottomSheetViewModel
import com.sampleapp.dialog.withviewmodel.SampleVMDialogViewModel
import com.sampleapp.fragmentactivity.SampleFragmentViewModel
import com.sampleapp.home.SampleHomeViewModel
import com.sampleapp.profile.SampleProfileViewModel
import com.sampleapp.samplefragment.SampleComposableFragment
import com.sampleapp.samplefragment.SampleComposableFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::SampleFragmentViewModel)
    scope<SampleComposableFragment> {
        viewModelOf(::SampleComposableFragmentViewModel)
    }
    viewModelOf(::SampleFragmentViewModel)
    viewModelOf(::SampleHomeViewModel)
    viewModelOf(::SampleProfileViewModel)
    viewModelOf(::SampleDetailViewModel)
    viewModelOf(::SampleBottomSheetViewModel)
    viewModelOf(::SampleVMDialogViewModel)
}