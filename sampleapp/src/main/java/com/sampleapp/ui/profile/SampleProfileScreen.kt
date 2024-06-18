package com.sampleapp.ui.profile

import androidx.compose.runtime.Composable
import com.sampleapp.R
import com.sampleapp.ui.base.SampleBaseScreen
import com.sampleapp.ui.profile.sections.ContentSection
import com.sampleapp.topbar.sampleBackToolbar
import com.vro.compose.preview.VROLightMultiDevicePreview

class SampleProfileScreen :
    SampleBaseScreen<SampleProfileState, SampleProfileEvents>() {

    @Composable
    override fun screenSections() = listOf(ContentSection())

    override fun setTopBar() = sampleBackToolbar(
        title = context.getString(R.string.profile_toolbar),
        onNavigation = {  navigateBack() }
    )

    @VROLightMultiDevicePreview
    @Composable
    override fun ScreenPreview() {
        CreatePreview()
    }
}