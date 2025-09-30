package com.sampleapp.bottombar

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.sampleapp.R
import com.vro.compose.components.VROBottomBar
import com.vro.compose.components.VROBottomBarItem

@Composable
fun SampleBottomBar(
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit,
    selectedItem: Int,
) = VROBottomBar(
    selectedItem = selectedItem,
    itemList = listOf(
        VROBottomBarItem(
            icon = R.drawable.ic_home,
            onClick = onHomeClick,
            iconSelectedTint = Color.Red,
            text = { Text(text = "Home") }
        ),
        VROBottomBarItem(
            icon = R.drawable.ic_profile,
            onClick = onProfileClick,
            iconSelectedTint = Color.Red,
            text = { Text(text = "Profile") }
        )
    ),
    background = Color(0xFFFFFFFF)
)