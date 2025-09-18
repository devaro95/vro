package com.sampleapp.ui.samplefragment.destination

import androidx.compose.runtime.Composable
import com.sampleapp.styles.theme.SampleTheme
import com.sampleapp.ui.samplefragment.destination.SampleComposableDestinationFragmentNavigator.SampleComposableDestinationFragmentDestinations
import com.sampleapp.ui.samplefragment.destination.screen.SampleComposableDestinationFragmentScreen
import com.vro.compose.VROComposableFragment
import com.vro.compose.theme.VROComposableMaterialTheme
import org.koin.android.ext.android.inject

class SampleComposableDestinationFragment :
    VROComposableFragment<SampleComposableDestinationFragmentViewModel,
            SampleComposableDestinationFragmentState,
            SampleComposableDestinationFragmentDestinations,
            SampleComposableDestinationFragmentScreen,
            SampleComposableDestinationFragmentEvents>() {

    override val navigator: SampleComposableDestinationFragmentNavigator by inject()

    override val viewModelSeed: SampleComposableDestinationFragmentViewModel by inject()

    override val theme: VROComposableMaterialTheme = SampleTheme

    @Composable
    override fun composableView() = SampleComposableDestinationFragmentScreen()
}