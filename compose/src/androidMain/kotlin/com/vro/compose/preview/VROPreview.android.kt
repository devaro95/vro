package com.vro.compose.preview

import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "Phone", device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480")
@Preview(name = "Tablet", device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480")
actual annotation class VROLightMultiDevicePreview
