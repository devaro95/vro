package com.sampleapp.di

import com.sampleapp.ui.detail.SampleDetailViewModel
import com.sampleapp.dialog.bottomsheet.SampleBottomSheetViewModel
import com.sampleapp.dialog.withviewmodel.SampleVMDialogViewModel
import com.sampleapp.ui.fragmentactivity.SampleFragmentViewModel
import com.sampleapp.ui.home.SampleHomeViewModel
import com.sampleapp.ui.profile.SampleProfileViewModel
import com.sampleapp.ui.samplefragment.SampleComposableFragment
import com.sampleapp.ui.samplefragment.SampleComposableFragmentViewModel
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