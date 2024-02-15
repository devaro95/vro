package com.sampleapp.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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

@ExperimentalMaterial3Api
class SampleProfileScreen :
    SampleBaseScreen<SampleProfileState, SampleDestinations, SampleProfileEvent>() {

    override val skeletonEnabled: Boolean = false

    @VROMultiDevicePreview
    @Composable
    override fun ComposablePreview() {
        GeneratePreview {
            ProfileContent(SampleProfileState.INITIAL)
        }
    }

    @Composable
    override fun ComposableContent(state: SampleProfileState) {
        ProfileContent(state)
    }

    override fun setTopBar() = sampleBackToolbar(
        title = context.getString(R.string.profile_toolbar),
        onNavigation = { navigateBack(true) }
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

    @Composable
    private fun ProfileContent(user: SampleProfileState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "This is the user profile")
        }
    }
}