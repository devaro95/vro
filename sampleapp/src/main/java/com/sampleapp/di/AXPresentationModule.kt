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
import com.sampleapp.ui.template.*
import com.sampleapp.ui.template.mapper.SampleTemplateMapper
import com.sampleapp.ui.template.mapper.SampleTemplateMapperImpl
import com.vro.core_android.navigation.VRONavigator
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.bind
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
    factoryOf(::SampleTemplateViewModel)


    scope<SampleComposableFragment> {
        scopedOf(::SampleComposableFragmentNavigator)
        factoryOf(::SampleComposableFragmentViewModel)
    }

    scope<SampleComposableDestinationFragment> {
        scopedOf(::SampleComposableDestinationFragmentNavigator)
        factoryOf(::SampleComposableDestinationFragmentViewModel)
    }

    scope<SampleTemplateContent> {
        scopedOf(::SampleTemplateViewModel)
        scopedOf(::SampleTemplateMapperImpl) bind SampleTemplateMapper::class
        scopedOf(::SampleTemplateNavigator) bind VRONavigator::class
    }
}