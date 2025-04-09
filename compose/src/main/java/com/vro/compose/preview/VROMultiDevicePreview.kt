/**
 * Package containing Compose preview annotations for multi-device and multi-theme previews.
 * These annotations help visualize components across different device sizes and themes.
 */
package com.vro.compose.preview

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

/**
 * Light theme preview annotation that shows components on multiple devices.
 * Includes previews for:
 * - Pixel 3 phone (light theme)
 * - Pixel Tablet (light theme)
 *
 * @see Preview for base annotation functionality
 * @see VROMultiDevicePreview for combined light/dark previews
 */
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

/**
 * Dark theme preview annotation that shows components on multiple devices.
 * Includes previews for:
 * - Pixel 3 phone (dark theme)
 * - Pixel Tablet (dark theme)
 *
 * @see Preview for base annotation functionality
 * @see VROMultiDevicePreview for combined light/dark previews
 */
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

/**
 * Combined preview annotation that includes both light and dark theme variants
 * across multiple devices. Combines [VROLightMultiDevicePreview] and
 * [VRODarkMultiDevicePreview] for comprehensive preview coverage.
 *
 * @see VROLightMultiDevicePreview for light theme previews
 * @see VRODarkMultiDevicePreview for dark theme previews
 */
@VROLightMultiDevicePreview
@VRODarkMultiDevicePreview
annotation class VROMultiDevicePreview