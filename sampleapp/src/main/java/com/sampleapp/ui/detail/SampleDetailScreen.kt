package com.sampleapp.ui.detail

import androidx.compose.runtime.Composable
import com.sampleapp.R
import com.sampleapp.ui.base.SampleBaseScreen
import com.sampleapp.ui.detail.sections.ContentSection
import com.sampleapp.topbar.sampleBackToolbar
import com.vro.compose.preview.VROLightMultiDevicePreview

class SampleDetailScreen : SampleBaseScreen<SampleDetailState, SampleDetailEvents>() {

    override fun setTopBar() = sampleBackToolbar(
        title = context.getString(R.string.detail_toolbar),
        onNavigation = { navigateBack() }
    )

    @Composable
    override fun screenSections() = listOf(ContentSection())

    @VROLightMultiDevicePreview
    @Composable
    override fun ScreenPreview() {
        CreatePreview()
    }
}