package com.sampleapp.bottombar

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.sampleapp.R
import com.vro.compose.components.VROBottomBar
import com.vro.compose.components.VROBottomBarItem

@Composable
fun SampleBottombar(
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit,
    selectedItem: Int,
) = VROBottomBar(
    selectedItem = selectedItem,
    itemList = listOf(
        VROBottomBarItem(
            icon = R.drawable.ic_home,
            onClick = onHomeClick,
            iconSelectedTint = MaterialTheme.colorScheme.secondary
        ),
        VROBottomBarItem(
            icon = R.drawable.ic_profile,
            onClick = onProfileClick,
            iconSelectedTint = MaterialTheme.colorScheme.secondary
        )
    )
)