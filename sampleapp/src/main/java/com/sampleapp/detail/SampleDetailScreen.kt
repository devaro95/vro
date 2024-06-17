package com.sampleapp.detail

import androidx.compose.runtime.Composable
import com.sampleapp.R
import com.sampleapp.base.SampleBaseScreen
import com.sampleapp.detail.sections.ContentSection
import com.sampleapp.topbar.sampleBackToolbar
import com.vro.compose.preview.VROLightMultiDevicePreview

class SampleDetailScreen : SampleBaseScreen<SampleDetailState, SampleDetailEvent>() {

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