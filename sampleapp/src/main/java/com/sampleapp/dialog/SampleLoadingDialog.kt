package com.sampleapp.dialog

import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.sampleapp.R
import com.vro.compose.preview.VROLightMultiDevicePreview

@VROLightMultiDevicePreview
@Composable
fun LoadingComposableDialogPreview() {
    LoadingDialog()
}

@Composable
fun LoadingDialog(
    isCancelable: Boolean = true,
) {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = isCancelable,
            dismissOnClickOutside = isCancelable
        )
    ) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.loading)
        )
        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever,
        )
        val dynamicProperties = rememberLottieDynamicProperties(
            rememberLottieDynamicProperty(
                property = LottieProperty.COLOR_FILTER,
                value = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                    MaterialTheme.colorScheme.secondary.hashCode(),
                    BlendModeCompat.SRC_ATOP
                ),
                keyPath = arrayOf("**")
            )
        )
        LottieAnimation(
            modifier = Modifier.size(230.dp),
            composition = composition,
            progress = progress,
            dynamicProperties = dynamicProperties
        )
    }
}