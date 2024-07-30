package com.sampleapp.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sampleapp.R
import com.sampleapp.topbar.sampleBackToolbar
import com.sampleapp.ui.base.SampleBaseScreen
import com.vro.compose.preview.VROLightMultiDevicePreview

class SampleDetailScreen : SampleBaseScreen<SampleDetailState, SampleDetailEvents>() {

    override fun setTopBar() = sampleBackToolbar(
        title = context.getString(R.string.detail_toolbar),
        onNavigation = { navigateBack() }
    )

    @Composable
    override fun ScreenContent(state: SampleDetailState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 32.dp)
        ) {
            Text(text = "This is the detail screen")
        }
    }

    @VROLightMultiDevicePreview
    @Composable
    override fun ScreenPreview() {
        ScreenContent(SampleDetailState.INITIAL)
    }
}