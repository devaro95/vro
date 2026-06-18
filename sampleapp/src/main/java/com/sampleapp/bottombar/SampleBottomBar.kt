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
import com.vro.compose.states.VROBottomBarBaseState

@Composable
fun SampleBottomBar(
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit,
    selectedItem: VROBottomBarBaseState.VROBottomBarValue,
) = VROBottomBar(
    selectedItem = selectedItem,
    itemList = listOf(
        VROBottomBarItem(
            value = SampleBottomBarValue.HOME,
            icon = R.drawable.ic_home,
            onClick = onHomeClick,
            iconSelectedTint = SampleAppTheme.colors.bottomBarSelected,
            text = { isSelected ->
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Home",
                    fontWeight = FontWeight.SemiBold,
                    color = (if (isSelected) SampleAppTheme.colors.bottomBarSelected else  Color(0xFF6B7280))
                )
            }
        ),
        VROBottomBarItem(
            value = SampleBottomBarValue.PROFILE,
            icon = R.drawable.ic_profile,
            onClick = onProfileClick,
            iconSelectedTint = SampleAppTheme.colors.bottomBarSelected,
            text = { isSelected ->
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Profile",
                    fontWeight = FontWeight.SemiBold,
                    color = (if (isSelected) SampleAppTheme.colors.bottomBarSelected else Color(0xFF6B7280))
                )
            }
        )
    ),
    height = 75.dp,
    background = SampleAppTheme.colors.bottomBarColor
)

enum class SampleBottomBarValue : VROBottomBarBaseState.VROBottomBarValue {
    HOME,
    PROFILE
}