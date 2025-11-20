package com.sampleapp.bottombar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sampleapp.R
import com.sampleapp.styles.theme.SampleAppTheme
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
            iconSelectedTint = SampleAppTheme.colors.bottomBarSelected,
            textColor = Color(0xFF6B7280),
            textSelectedColor = SampleAppTheme.colors.bottomBarSelected,
            text = { color, selectedColor ->
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Home",
                    fontWeight = FontWeight.SemiBold,
                    color = (if (selectedItem == 1) selectedColor else color) ?: Color.Black
                )
            }
        ),
        VROBottomBarItem(
            icon = R.drawable.ic_profile,
            onClick = onProfileClick,
            iconSelectedTint = SampleAppTheme.colors.bottomBarSelected,
            textColor = Color(0xFF6B7280),
            textSelectedColor = SampleAppTheme.colors.bottomBarSelected,
            text = { color, selectedColor ->
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Profile",
                    fontWeight = FontWeight.SemiBold,
                    color = (if (selectedItem == 1) selectedColor else color) ?: Color.Black
                )
            }
        )
    ),
    height = 75.dp,
    background = SampleAppTheme.colors.bottomBarColor
)