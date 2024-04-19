package com.sampleapp.profile

import androidx.compose.runtime.Composable
import com.sampleapp.R
import com.sampleapp.base.SampleBaseScreen
import com.sampleapp.main.SampleDestinations
import com.sampleapp.profile.sections.ContentSection
import com.sampleapp.topbar.sampleBackToolbar
import com.vro.compose.preview.VROLightMultiDevicePreview

class SampleProfileScreen :
    SampleBaseScreen<SampleProfileState, SampleDestinations, SampleProfileEvent>() {

    @Composable
    override fun composableContent() = listOf(ContentSection())

    override fun setTopBar() = sampleBackToolbar(
        title = context.getString(R.string.profile_toolbar),
        onNavigation = { navigateBack() }
    )


    @VROLightMultiDevicePreview
    @Composable
    override fun ComposablePreview() {
        CreatePreview()
    }
}