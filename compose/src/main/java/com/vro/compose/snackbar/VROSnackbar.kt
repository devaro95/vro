package com.vro.compose.snackbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * A customized Snackbar composable with configurable colors and padding.
 *
 * This composable provides a styled snackbar with default white background and customizable
 * text/action colors, following the application's design system.
 *
 * @param data The [SnackbarData] containing the message and action details
 * @param backgroundColor The background color of the snackbar (default: White - 0xFFFFFFFF)
 * @param textColor The color of the snackbar message text (required)
 * @param actionButtonColor The color of the action button text (required)
 *
 * @see SnackbarData for base snackbar content information
 * @see Snackbar for the underlying Material3 implementation
 */
@Composable
fun VROSnackbar(
    data: SnackbarData,
    backgroundColor: Color = Color(0xFFFFFFFF),
    textColor: Color,
    actionButtonColor: Color,
) {
    Snackbar(
        modifier = Modifier.padding(16.dp),
        containerColor = backgroundColor,
        action = {
            data.visuals.actionLabel?.let { actionLabel ->
                TextButton(onClick = { data.performAction() }) {
                    Text(
                        text = actionLabel,
                        color = actionButtonColor
                    )
                }
            }
        }
    ) {
        Text(
            text = data.visuals.message,
            color = textColor
        )
    }
}