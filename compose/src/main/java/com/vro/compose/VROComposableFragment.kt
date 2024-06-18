package com.vro.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.fragment.findNavController
import com.vro.compose.screen.VROFragmentScreen
import com.vro.event.VROEvent
import com.vro.fragment.VROFragmentBuilder
import com.vro.fragment.VROInjectionFragment
import com.vro.fragment.VROViewModel
import com.vro.navigation.VROBackResult
import com.vro.navigation.VRODestination
import com.vro.navigation.VROFragmentNavigator.Companion.NAVIGATION_STATE
import com.vro.state.VROState

abstract class VROComposableFragment<
        VM : VROViewModel<S, D, E>,
        S : VROState,
        D : VRODestination,
        SC : VROFragmentScreen<S, D, E>,
        E : VROEvent,
        >
    : VROInjectionFragment<VM>(), VROFragmentBuilder<VM, S, D, E> {

    override val state: S? by lazy { restoreArguments() }

    open val theme: VROComposableTheme? = null

    @Suppress("UNCHECKED_CAST")
    private fun restoreArguments(): S? = arguments?.getSerializable(NAVIGATION_STATE) as? S

    @Composable
    abstract fun composableView(): SC

    @Composable
    private fun CreateTheme(
        lightColors: ColorScheme,
        darkColors: ColorScheme,
        typography: Typography,
        content: @Composable () -> Unit,
    ) {
        MaterialTheme(
            colorScheme = if (isSystemInDarkTheme()) darkColors else lightColors,
            typography = typography,
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
                        composableView().CreateScreen(viewModel)
                    }
                } ?: run {
                    composableView().CreateScreen(viewModel)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreatedVro(viewModel, findNavController(), viewLifecycleOwner)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    fun event(event: E) {
        viewModel.eventListener(event)
    }

    fun navigateBack(result: VROBackResult?) {
        viewModel.eventBack(result)
    }
}