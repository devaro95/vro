package com.vro.compose.states

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.graphics.Color

/**
 * Represents the visual and behavioral state of a snackbar component.
 *
 * @property hostState The [SnackbarHostState] that controls the snackbar's presentation and dismissal
 * @property textColor The color of the snackbar's text message (default: [Color.Black])
 * @property backgroundColor The background color of the snackbar (default: [Color.Black])
 * @property actionColor The color of the snackbar's action button (default: [Color.Black])
 *
 */
data class VROSnackBarState(
    val hostState: SnackbarHostState,
    val textColor: Color = Color.Black,
    val backgroundColor: Color = Color.Black,
    val actionColor: Color = Color.Black,
)