package com.sampleapp.detail

import androidx.compose.runtime.Composable
import com.sampleapp.R
import com.sampleapp.base.SampleBaseScreen
import com.sampleapp.detail.sections.ContentSection
import com.sampleapp.main.SampleDestinations
import com.sampleapp.topbar.sampleBackToolbar
import com.vro.compose.model.VROOrientation
import com.vro.compose.model.VROOrientation.VERTICAL
import com.vro.compose.preview.VROMultiDevicePreview

class SampleDetailScreen :
    SampleBaseScreen<SampleDetailState, SampleDestinations, SampleDetailEvent>(
        orientation = VERTICAL
    ) {

    override fun setTopBar() = sampleBackToolbar(
        title = context.getString(R.string.detail_toolbar),
        onNavigation = { navigateBack() }
    )

    @Composable
    override fun composableContent() = listOf(ContentSection())

    @VROMultiDevicePreview
    @Composable
    override fun ComposablePreview() {
        CreatePreview()
    }
}