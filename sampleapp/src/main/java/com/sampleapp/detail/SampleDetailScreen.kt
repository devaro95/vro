package com.sampleapp.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sampleapp.R
import com.sampleapp.base.SampleBaseScreen
import com.sampleapp.components.SampleTextSkeleton
import com.sampleapp.main.SampleDestinations
import com.sampleapp.topbar.sampleBackToolbar
import com.vro.compose.extensions.GeneratePreview
import com.vro.compose.preview.VROMultiDevicePreview

class SampleDetailScreen :
    SampleBaseScreen<SampleDetailState, SampleDestinations, SampleDetailEvent>() {

    @VROMultiDevicePreview
    @Composable
    override fun ComposablePreview() {
        GeneratePreview {
            ComposableContent(SampleDetailState.INITIAL)
        }
    }

    @Composable
    override fun ComposableContent(state: SampleDetailState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Detail Screen Test")
        }
    }

    override fun setTopBar() = sampleBackToolbar(
        title = context.getString(R.string.detail_toolbar),
        onNavigation = { navigateBack() }
    )

    @Composable
    override fun ComposableSkeleton() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            SampleTextSkeleton(width = 200.dp)
        }
    }
}