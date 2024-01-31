package com.sampleapp.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sampleapp.base.SampleBaseScreen
import com.sampleapp.components.SampleButtonSkeleton
import com.sampleapp.components.SampleTextSkeleton
import com.sampleapp.main.SampleDestinations
import com.sampleapp.topBar.sampleHomeToolbar
import com.vro.compose.extensions.GeneratePreview
import com.vro.compose.preview.VROMultiDevicePreview

@ExperimentalMaterial3Api
class SampleHomeScreen : SampleBaseScreen<SampleHomeState, SampleDestinations, SampleHomeEvent>() {

    @Composable
    @VROMultiDevicePreview
    override fun ComposablePreview() {
        GeneratePreview {
            ComposableContent(SampleHomeState.INITIAL)
        }
    }

    @Composable
    override fun ComposableContent(state: SampleHomeState) {
        HomeScreenContent(state)
    }

    @Composable
    override fun ComposableSkeleton() {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SampleTextSkeleton(width = 100.dp)
            SampleButtonSkeleton(modifier = Modifier.padding(top = 16.dp), width = 100.dp)
        }
    }

    override fun setTopBar() =
        context.sampleHomeToolbar(
            onAction = { eventLauncher.navigateToProfile() },
            onNavigation = { navigateBack() }
        )

    @Composable
    private fun HomeScreenContent(state: SampleHomeState) {
        Column(
            Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = state.text,
                textAlign = TextAlign.Center
            )
            OutlinedButton(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                onClick = { eventLauncher.onActionButtonClick() }
            ) {
                Text(text = "Update text")
            }
            LazyColumn {
                items(getItems()) {
                    Text(text = it)
                }
            }
        }
    }

    private fun getItems(): List<String> {
        return listOf(
            "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a"
        )
    }
}