package com.vro.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.states.VROTopBarState
import com.vro.compose.states.VROTopBarState.VROTopBarButton

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
                },
                navigationButton = VROTopBarButton(
                    iconVector = Icons.AutoMirrored.Filled.ArrowBack,
                    iconSize = 16.dp,
                    onClick = { }
                )
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
        actions = { SetTopBarButton(state.actionButton) },
        navigationIcon = { SetTopBarButton(state.navigationButton) },
        modifier = modifier.setHeight(state.height)
    )
}

@Composable
private fun SetTopBarButton(button: VROTopBarButton?) {
    button?.let {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = it.onClick) {
                button.icon?.let { icon ->
                    Icon(
                        painter = painterResource(id = icon),
                        modifier = Modifier.size(button.iconSize),
                        contentDescription = null,
                        tint = button.tint ?: LocalContentColor.current
                    )
                } ?: run {
                    button.iconVector?.let { imageVector ->
                        Icon(
                            imageVector = imageVector,
                            modifier = Modifier.size(button.iconSize),
                            contentDescription = null,
                            tint = button.tint ?: LocalContentColor.current
                        )
                    }
                }
            }
        }
    }
}

fun Modifier.setHeight(height: Dp?): Modifier {
    return height?.let { height(it) } ?: this
}
