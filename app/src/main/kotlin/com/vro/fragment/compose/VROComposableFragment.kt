package com.vro.fragment.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.vro.event.VROEvent
import com.vro.fragment.VROFragmentBuilder
import com.vro.fragment.VROInjectionFragment
import com.vro.fragment.VROViewModel
import com.vro.navigation.VRODestination
import com.vro.navigation.VROFragmentNavigator.Companion.NAVIGATION_STATE
import com.vro.state.VROState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class VROComposableFragment<
        VM : VROViewModel<S, D, E>,
        S : VROState,
        D : VRODestination,
        SC : VROScreen<VM, S, D, E>,
        E : VROEvent,
        >
    : VROInjectionFragment<VM>(), VROFragmentBuilder<VM, S, D, E> {

    override val state: S? by lazy { restoreArguments() }

    abstract val theme: VroComposableTheme?

    @Suppress("UNCHECKED_CAST")
    private fun restoreArguments(): S? = arguments?.getSerializable(NAVIGATION_STATE) as? S

    @Composable
    abstract fun composableView(): SC

    abstract fun onStateUpdated()

    @Composable
    private fun CreateTheme(
        lightColors: Colors,
        darkColors: Colors,
        typography: Typography,
        content: @Composable () -> Unit,
    ) {
        MaterialTheme(
            colors = if (isSystemInDarkTheme()) darkColors else lightColors,
            typography = typography
        ) {
            CompositionLocalProvider(
                content = content
            )
        }
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeState(viewModel, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                theme?.also {
                    CreateTheme(it.lightColors, it.darkColors, it.typography) {
                        composableView().CreateScreen(viewModel, state)
                    }
                } ?: run {
                    composableView().CreateScreen(viewModel, state)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreatedVro(viewModel, findNavController(), viewLifecycleOwner)
    }

    private fun setStateObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest {
                onStateUpdated()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setStateObserver()
        onResumeVro(viewModel)
    }

    fun viewModelEvent(event: E) {
        viewModel.eventListener(event)
    }
}