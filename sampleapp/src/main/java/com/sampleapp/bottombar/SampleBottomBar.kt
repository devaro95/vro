package com.sampleapp.bottombar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sampleapp.R
import com.sampleapp.styles.theme.SampleAppTheme
import com.vro.compose.components.VROBottomBarItem
import com.vro.compose.composition.LocalBottomBarScrollProgress
import com.vro.compose.states.VROBottomBarBaseState
import com.vro.constants.EMPTY_STRING

// Floating, fully rounded bottom bar (same look as AXClimb's AXBottomBar) built directly here
// instead of through the generic VROBottomBar component, so this shape stays specific to the
// sample app and doesn't change how VROBottomBar renders for other consumers of the library.
private val BottomBarShape = RoundedCornerShape(percent = 50)
private val IndicatorSize = 48.dp
private val UnselectedColor = Color(0xFF6B7280)

// The bar's own container shrinks on scroll-down, both vertically and horizontally -- items keep
// their size and stay centered, since Row/Column centering is computed against whatever size the
// bar actually has.
private val ExpandedBarHeight = 56.dp
private val CollapsedBarHeight = 50.dp
private const val ExpandedWidthFraction = 1f
private const val CollapsedWidthFraction = 0.75f

@Composable
fun SampleBottomBar(
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit,
    selectedItem: VROBottomBarBaseState.VROBottomBarValue,
) {
    // Shrinks the bar's own height as the user scrolls down (matches AXClimb's AXBottomBar) and
    // grows it back on scroll-up. scrollProgress comes from VRO's Activity (0f..1f); it stays 0f
    // wherever the opt-in isn't enabled, so the bar simply never collapses.
    val scrollProgress = LocalBottomBarScrollProgress.current
    val barHeight by animateDpAsState(
        targetValue = ExpandedBarHeight - (ExpandedBarHeight - CollapsedBarHeight) * scrollProgress,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMedium,
        ),
        label = EMPTY_STRING,
    )
    val barWidthFraction by animateFloatAsState(
        targetValue = ExpandedWidthFraction - (ExpandedWidthFraction - CollapsedWidthFraction) * scrollProgress,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMedium,
        ),
        label = EMPTY_STRING,
    )
    // Text collapses out of the layout (not just fades) as the bar collapses, so only the icon
    // remains once fully scrolled down. AnimatedVisibility shrinks its measured height to zero as
    // it exits, which is what lets the Icon end up truly centered by itself once the text is gone
    // -- fading alpha alone would keep reserving the text's layout space.
    val textVisible = scrollProgress < 0.5f

    val items = listOf(
        VROBottomBarItem(
            value = SampleBottomBarValue.HOME,
            icon = R.drawable.ic_home,
            onClick = onHomeClick,
            iconSize = 20.dp,
            text = {
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = "Home",
                    fontWeight = FontWeight.SemiBold,
                    color = UnselectedColor
                )
            }
        ),
        VROBottomBarItem(
            value = SampleBottomBarValue.PROFILE,
            icon = R.drawable.ic_profile,
            onClick = onProfileClick,
            iconSize = 20.dp,
            text = {
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = "Profile",
                    fontWeight = FontWeight.SemiBold,
                    color = UnselectedColor
                )
            }
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(barWidthFraction)
                .height(barHeight),
            shape = BottomBarShape,
            color = SampleAppTheme.colors.bottomBarColor,
            shadowElevation = 8.dp,
            tonalElevation = 0.dp,
        ) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val itemWidth = maxWidth / items.size
                val selectedIndex = items.indexOfFirst { it.value == selectedItem }.coerceAtLeast(0)
                val indicatorOffsetX by animateDpAsState(
                    targetValue = itemWidth * selectedIndex,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow,
                    ),
                    label = EMPTY_STRING,
                )

                Box(
                    modifier = Modifier
                        .offset(x = indicatorOffsetX)
                        .width(itemWidth)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .size(IndicatorSize)
                            .background(
                                color = SampleAppTheme.colors.bottomBarSelected.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(percent = 50),
                            ),
                    )
                }

                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    items.forEach { item ->
                        val isSelected = selectedItem == item.value
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable(
                                    onClick = { item.onClick?.invoke() },
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                ),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = EMPTY_STRING,
                                modifier = Modifier.size(item.iconSize),
                                tint = UnselectedColor,
                            )
                            AnimatedVisibility(
                                visible = textVisible,
                                enter = fadeIn() + expandVertically(),
                                exit = fadeOut() + shrinkVertically(),
                            ) {
                                item.text.invoke(isSelected)
                            }
                        }
                    }
                }
            }
        }
    }
}

enum class SampleBottomBarValue : VROBottomBarBaseState.VROBottomBarValue {
    HOME,
    PROFILE
}
