package com.sampleapp.ui.samplefragment.destination

import androidx.compose.runtime.Composable
import com.sampleapp.styles.SampleTheme
import com.sampleapp.ui.samplefragment.destination.SampleComposableDestinationFragmentNavigator.SampleComposableDestinationFragmentDestinations
import com.vro.compose.VROComposableFragment
import com.vro.compose.VROComposableTheme
import org.koin.android.ext.android.inject

class SampleComposableDestinationFragment :
    VROComposableFragment<SampleComposableDestinationFragmentViewModel,
            SampleComposableDestinationFragmentState,
            SampleComposableDestinationFragmentDestinations,
            SampleComposableDestinationFragmentScreen,
            SampleComposableDestinationFragmentEvents>() {

    override val navigator: SampleComposableDestinationFragmentNavigator by inject()

    override val viewModelSeed: SampleComposableDestinationFragmentViewModel by inject()

    override val theme: VROComposableTheme = SampleTheme

    @Composable
    override fun composableView() = SampleComposableDestinationFragmentScreen()
}