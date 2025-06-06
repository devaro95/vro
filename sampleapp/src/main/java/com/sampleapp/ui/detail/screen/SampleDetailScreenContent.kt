package com.sampleapp.ui.detail.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sampleapp.R
import com.sampleapp.topbar.sampleBackToolbar
import com.sampleapp.ui.detail.SampleDetailEvents
import com.sampleapp.ui.detail.SampleDetailState
import com.vro.compose.screen.VROScreenContent
import com.vro.compose.states.VROTopBarBaseState

class SampleDetailScreenContent : VROScreenContent<SampleDetailState, SampleDetailEvents>() {

    override fun setTopBar(currentState: VROTopBarBaseState) = sampleBackToolbar(
        title = context.getString(R.string.detail_toolbar),
        onNavigation = { navigateBack() }
    )

    @Composable
    override fun Content(state: SampleDetailState) {
        Column(
            modifier = Modifier.Companion
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 32.dp)
        ) {
            Text(text = state.text)
        }
    }

    @Composable
    override fun ScreenPreview() {
        Content(state = SampleDetailState.Companion.INITIAL)
    }
}