package com.sampleapp.di

import com.sampleapp.dialog.bottomsheet.SampleBottomSheetNavViewModel
import com.sampleapp.dialog.bottomsheet.SampleBottomSheetViewModel
import com.sampleapp.dialog.withviewmodel.SampleVMDialogViewModel
import com.sampleapp.ui.container.SampleContainerViewModel
import com.sampleapp.ui.detail.SampleDetailViewModel
import com.sampleapp.ui.fragmentactivity.SampleFragmentViewModel
import com.sampleapp.ui.home.SampleHomeViewModel
import com.sampleapp.ui.profile.SampleProfileViewModel
import com.sampleapp.ui.samplefragment.*
import com.sampleapp.ui.samplefragment.destination.*
import com.sampleapp.ui.template.SampleTemplate
import com.sampleapp.ui.template.mapper.SampleTemplateMapper
import com.sampleapp.ui.template.mapper.SampleTemplateMapperImpl
import com.sampleapp.ui.template.SampleTemplateNavigator
import com.sampleapp.ui.template.SampleTemplateViewModel
import com.vro.compose.navigator.VROTemplateNav
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.scopedOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val axPresentationModule = module {
    factoryOf(::SampleFragmentViewModel)
    factoryOf(::SampleFragmentViewModel)
    factoryOf(::SampleHomeViewModel)
    factoryOf(::SampleProfileViewModel)
    factoryOf(::SampleContainerViewModel)
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

    scope<SampleTemplate> {
        scopedOf(::SampleTemplateViewModel)
        scopedOf(::SampleTemplateMapperImpl) bind SampleTemplateMapper::class
        scopedOf(::SampleTemplateNavigator) bind VROTemplateNav::class
    }
}