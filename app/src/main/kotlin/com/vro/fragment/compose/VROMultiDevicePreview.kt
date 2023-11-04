package com.vro.fragment.compose

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    showBackground = true,
    device = Devices.PIXEL_3,
    backgroundColor = 0xFFFFFFFF
)
@Preview(
    showBackground = true,
    device = Devices.PIXEL_5,
    backgroundColor = 0xFFFFFFFF
)
annotation class VROMultiDevicePreview