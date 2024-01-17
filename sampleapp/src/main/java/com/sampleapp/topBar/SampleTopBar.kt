package com.sampleapp.topBar

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.unit.dp
import com.sampleapp.R
import com.vro.compose.states.VROComposableScaffoldState.VROTopBarState

fun Context.sampleHomeToolbar(onAction: () -> Unit, onNavigation: () -> Unit) = VROTopBarState(
    title = getString(R.string.home_toolbar),
    actionButton = VROTopBarState.VROTopBarButton(
        iconVector = Icons.Default.Person,
        iconSize = 26.dp,
        onClick = onAction
    ),
    navigationButton = VROTopBarState.VROTopBarButton(
        iconVector = Icons.Default.ArrowBack,
        iconSize = 26.dp,
        onClick = onNavigation
    )
)

fun sampleBackToolbar(title: String, onNavigation: () -> Unit) = VROTopBarState(
    title = title,
    navigationButton = VROTopBarState.VROTopBarButton(
        iconVector = Icons.Default.ArrowBack,
        iconSize = 16.dp,
        onClick = onNavigation
    )
)