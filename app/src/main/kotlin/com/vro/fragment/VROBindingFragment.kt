package com.vro.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.navigation.VROFragmentNavigator
import com.vro.navigation.VROFragmentNavigator.Companion.NAVIGATION_STATE
import com.vro.state.VROState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class VROBindingFragment<
        VM : VROViewModel<S, D, E>,
        VB : ViewBinding,
        S : VROState,
        D : VRODestination,
        E : VROEvent> : VROInjectionFragment<VM>() {

    val state: S? by lazy { restoreArguments() }

    @Suppress("UNCHECKED_CAST")
    private fun restoreArguments(): S? = arguments?.getSerializable(NAVIGATION_STATE) as? S

    private var _binding: VB? = null

    val binding get() = _binding!!

    abstract fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    fun initializeState() {
        viewModel.onInitializeState()
        viewModel.setInitialState(state)
    }

    fun setViewBindingObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest {
                onViewUpdate(binding, it)
            }
        }
        viewModel.errorState.observe(this) {
            binding.onError(it)
        }
    }

    abstract fun VB.onViewStarted()

    abstract fun onViewUpdate(binding: VB, data: S)

    abstract fun VB.onError(error: Throwable)

    fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): View {
        _binding = createViewBinding(inflater, container)
        return binding.root
    }
}