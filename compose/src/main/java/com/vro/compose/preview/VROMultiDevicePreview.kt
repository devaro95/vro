package com.vro.compose.preview

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    showBackground = true,
    device = Devices.PIXEL_3,
    backgroundColor = 0xFFFFFFFF,
    name = "Pixel 3 Light"
)
@Preview(
    showBackground = true,
    device = Devices.PIXEL_TABLET,
    backgroundColor = 0xFFFFFFFF,
    name = "Pixel Tablet Light"
)
annotation class VROLightMultiDevicePreview

@Preview(
    showBackground = true,
    device = Devices.PIXEL_3,
    backgroundColor = 0xFF000000,
    name = "Pixel 3 Dark"
)
@Preview(
    showBackground = true,
    device = Devices.PIXEL_TABLET,
    backgroundColor = 0xFF000000,
    name = "Pixel Tablet Dark"
)
annotation class VRODarkMultiDevicePreview

@VROLightMultiDevicePreview
@VRODarkMultiDevicePreview
annotation class VROMultiDevicePreview