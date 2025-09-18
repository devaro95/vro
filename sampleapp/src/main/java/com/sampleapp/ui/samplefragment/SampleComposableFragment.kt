package com.sampleapp.ui.samplefragment

import androidx.compose.runtime.Composable
import com.sampleapp.styles.theme.SampleTheme
import com.sampleapp.ui.samplefragment.SampleComposableFragmentNavigator.SampleComposableFragmentDestinations
import com.sampleapp.ui.samplefragment.screen.SampleComposableFragmentScreen
import com.vro.compose.VROComposableFragment
import com.vro.compose.theme.VROComposableMaterialTheme
import org.koin.android.ext.android.inject

class SampleComposableFragment :
    VROComposableFragment<SampleComposableFragmentViewModel,
            SampleComposableFragmentState,
            SampleComposableFragmentDestinations,
            SampleComposableFragmentScreen,
            SampleComposableFragmentEvents>() {

    override val navigator: SampleComposableFragmentNavigator by inject()

    override val viewModelSeed: SampleComposableFragmentViewModel by inject()

    override val theme: VROComposableMaterialTheme = SampleTheme

    @Composable
    override fun composableView() = SampleComposableFragmentScreen()
}