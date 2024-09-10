package com.vro.compose.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.states.VROTopBarState

@Composable
@VROLightMultiDevicePreview
fun VroTopBarPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        VroTopBar(
            state = VROTopBarState(
                title = {
                    Text(
                        text = "Title",
                        color = Color.Blue
                    )
                }
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VroTopBar(
    modifier: Modifier = Modifier,
    state: VROTopBarState,
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = state.background ?: MaterialTheme.colorScheme.background
        ),
        title = {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
            ) {
                state.title?.invoke()
            }
        },
        actions = { state.actionButton?.invoke(this) },
        navigationIcon = { state.navigationButton?.invoke() },
        modifier = modifier.setHeight(state.height)
    )
}

fun Modifier.setHeight(height: Dp?): Modifier {
    return height?.let { height(it) } ?: this
}
