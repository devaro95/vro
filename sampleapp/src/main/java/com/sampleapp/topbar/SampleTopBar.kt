package com.sampleapp.topbar

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sampleapp.R
import com.vro.compose.states.VROTopBarBaseState.VROTopBarButton
import com.vro.compose.states.VROTopBarBaseState.VROTopBarState

fun sampleHomeToolbar(context: Context, onAction: () -> Unit) = VROTopBarState(
    title = {
        Text(
            text = context.getString(R.string.home_toolbar),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    },
    actionButton = {
        SetTopBarButton(
            VROTopBarButton(
                iconVector = Icons.Default.Person,
                iconSize = 26.dp,
                onClick = onAction
            )
        )
    }
)

fun sampleBackToolbar(title: String, onNavigation: () -> Unit) = VROTopBarState(
    title = {
        Text(
            text = title,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    },
    navigationButton = {
        SetTopBarButton(
            VROTopBarButton(
                iconVector = Icons.AutoMirrored.Filled.ArrowBack,
                iconSize = 16.dp,
                onClick = onNavigation
            )
        )
    }
)

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