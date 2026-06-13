package com.vro.compose.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.vro.compose.states.VROTopBarBaseState.VROTopBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VroTopBar(modifier: Modifier = Modifier, state: VROTopBarState) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = state.background ?: MaterialTheme.colorScheme.background
        ),
        title = {
            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                state.title?.invoke()
            }
        },
        actions = { state.actionButton?.invoke(this) },
        navigationIcon = { state.navigationButton?.invoke() },
        modifier = modifier.setHeight(state.height)
    )
}

fun Modifier.setHeight(height: Dp?): Modifier = height?.let { height(it) } ?: this
