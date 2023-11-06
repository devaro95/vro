package com.vro.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class VROBindingFragment<
        VM : VROViewModel<S, D, E>,
        VB : ViewBinding,
        S : VROState,
        D : VRODestination,
        E : VROEvent> : VROInjectionFragment<VM>() {

    private var _binding: VB? = null

    val binding get() = _binding!!

    abstract fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    fun setViewBindingObservers(viewModel: VM, binding: VB, fragment: Fragment) {
        fragment.viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest {
                onViewUpdate(binding, it)
            }
        }
        viewModel.errorState.observe(fragment) {
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