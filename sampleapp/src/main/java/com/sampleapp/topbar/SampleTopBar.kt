package com.sampleapp.topbar

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.unit.dp
import com.sampleapp.R
import com.vro.compose.states.VROTopBarState

fun sampleHomeToolbar(context: Context, onAction: () -> Unit) = VROTopBarState(
    title = context.getString(R.string.home_toolbar),
    actionButton = VROTopBarState.VROTopBarButton(
        iconVector = Icons.Default.Person,
        iconSize = 26.dp,
        onClick = onAction
    )
)

fun sampleBackToolbar(title: String, onNavigation: () -> Unit) = VROTopBarState(
    title = title,
    navigationButton = VROTopBarState.VROTopBarButton(
        iconVector = Icons.AutoMirrored.Filled.ArrowBack,
        iconSize = 16.dp,
        onClick = onNavigation
    )
)