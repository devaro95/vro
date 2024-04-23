package com.sampleapp.home

import androidx.compose.runtime.Composable
import com.sampleapp.base.SampleBaseScreen
import com.sampleapp.home.sections.BottomButtonSection
import com.sampleapp.home.sections.TopButtonSection
import com.sampleapp.main.SampleDestinations
import com.sampleapp.topbar.sampleHomeToolbar
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.preview.VROMultiDevicePreview

class SampleHomeScreen : SampleBaseScreen<SampleHomeState, SampleHomeEvents>() {

    override fun setTopBar() =
        sampleHomeToolbar(
            context = context,
            onAction = { event(SampleHomeEvents.ProfileNavigation) }
        )

    @Composable
    override fun composableContent() =
        listOf(
            TopButtonSection(),
            BottomButtonSection()
        )

    @Composable
    override fun composableTabletContent() =
        listOf(
            TopButtonSection(),
            BottomButtonSection()
        )

    @Composable
    @VROLightMultiDevicePreview
    override fun ComposablePreview() {
        CreatePreview()
    }
}