package com.sampleapp.ui.samplefragment

import androidx.compose.runtime.Composable
import com.sampleapp.ui.samplefragment.SampleComposableFragmentNavigator.SampleComposableFragmentDestinations
import com.vro.compose.VROComposableFragment
import com.vro.state.VRODialogState
import org.koin.android.ext.android.inject

class SampleComposableFragment :
    VROComposableFragment<SampleComposableFragmentViewModel,
            SampleComposableFragmentState,
            SampleComposableFragmentDestinations,
            SampleComposableFragmentScreen,
            SampleComposableFragmentEvents>() {

    override val navigator: SampleComposableFragmentNavigator by inject()

    override val viewModelSeed: SampleComposableFragmentViewModel by inject()

    override fun onLoadDialog(data: VRODialogState) = Unit

    @Composable
    override fun composableView() = SampleComposableFragmentScreen()
}