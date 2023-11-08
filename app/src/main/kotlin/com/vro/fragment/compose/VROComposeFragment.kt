package com.vro.fragment.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.fragment.findNavController
import com.vro.event.VROEvent
import com.vro.fragment.VROFragmentBuilder
import com.vro.fragment.VROInjectionFragment
import com.vro.fragment.VROViewModel
import com.vro.navigation.VRODestination
import com.vro.state.VROState

abstract class VROComposeFragment<
        VM : VROViewModel<S, D, E>,
        S : VROState,
        D : VRODestination,
        SC : VROScreen<VM, S, D, E>,
        E : VROEvent,
        >
    : VROInjectionFragment<VM>(), VROFragmentBuilder<VM, S, D, E> {

    @Composable
    abstract fun composableView(): SC

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                composableView().CreateScreen(viewModel)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreatedVro(viewModel, findNavController(), viewLifecycleOwner)
    }

    override fun onResume() {
        super.onResume()
        onResumeVro(viewModel, this)
    }

    fun viewModelEvent(event: E) {
        viewModel.eventListener(event)
    }
}