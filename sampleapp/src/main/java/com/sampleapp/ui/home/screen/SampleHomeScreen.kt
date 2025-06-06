package com.sampleapp.ui.home.screen

import com.sampleapp.ui.base.SampleBaseScreen
import com.sampleapp.ui.home.SampleHomeEvents
import com.sampleapp.ui.home.SampleHomeState
import com.sampleapp.ui.home.handler.SampleHomeDialogHandler
import com.sampleapp.ui.home.handler.SampleHomeOneTimeHandler
import com.vro.compose.screen.VROScreenContent
import com.vro.compose.handler.VROOneTimeHandler
import com.vro.compose.skeleton.VROSkeleton

class SampleHomeScreen(
    override val screenContent: VROScreenContent<SampleHomeState, SampleHomeEvents> = SampleHomeScreenContent(),
    override val skeleton: VROSkeleton = SampleHomeSkeleton(),
    override val dialogHandler: SampleHomeDialogHandler = SampleHomeDialogHandler(),
    override val oneTimeHandler: VROOneTimeHandler<SampleHomeState, SampleHomeEvents> = SampleHomeOneTimeHandler(),
) : SampleBaseScreen<SampleHomeState, SampleHomeEvents>()