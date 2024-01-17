package com.vro.compose.components

import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.vro.compose.states.VROComposableScaffoldState.VROTopBarState.VROTopBarButton

@ExperimentalMaterial3Api
@Composable
fun VroTopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    titleSize: TextUnit = 14.sp,
    actionButton: VROTopBarButton? = null,
    navigationButton: VROTopBarButton? = null,
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                text = title,
                fontSize = titleSize
            )
        },
        actions = { SetTopBarButton(actionButton) },
        navigationIcon = { SetTopBarButton(navigationButton) },
        modifier = modifier
    )
}

@Composable
private fun SetTopBarButton(button: VROTopBarButton?) {
    button?.let {
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
