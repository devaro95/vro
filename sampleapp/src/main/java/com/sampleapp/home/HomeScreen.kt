package com.sampleapp.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.sampleapp.base.BaseScreen
import com.sampleapp.home.HomeEvent.ButtonClick
import com.sampleapp.main.Destinations
import com.vro.compose.extensions.GeneratePreview
import com.vro.compose.preview.VROMultiDevicePreview

class HomeScreen : BaseScreen<HomeViewModel, HomeState, Destinations, HomeEvent>() {

    @Composable
    @VROMultiDevicePreview
    override fun ComposablePreview() {
        GeneratePreview {
            ComposableContent(HomeState.INITIAL)
        }
    }

    @Composable
    override fun ComposableContent(state: HomeState) {
        HomeScreenContent(state)
    }

    @Composable
    private fun HomeScreenContent(state: HomeState) {
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
            Button(onClick = { viewModelEvent(ButtonClick) }) {
                Text(text = "Change text")
            }
            Button(onClick = { navigate(Destinations.Profile) }) {
                Text(text = "Go to profile")
            }
        }
    }
}