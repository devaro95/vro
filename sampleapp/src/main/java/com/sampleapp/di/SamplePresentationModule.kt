package com.sampleapp.di

import com.sampleapp.dialog.bottomsheet.SampleBottomSheetViewModel
import com.sampleapp.home.SampleHomeViewModel
import com.sampleapp.profile.SampleProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::SampleHomeViewModel)
    viewModelOf(::SampleProfileViewModel)
    viewModelOf(::SampleBottomSheetViewModel)
}