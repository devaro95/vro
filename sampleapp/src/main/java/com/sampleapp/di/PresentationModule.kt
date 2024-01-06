package com.sampleapp.di

import com.sampleapp.home.HomeViewModel
import com.sampleapp.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::ProfileViewModel)
}