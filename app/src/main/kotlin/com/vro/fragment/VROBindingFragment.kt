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
import kotlinx.coroutines.launch

interface VROBindingFragment<VM : VROViewModel<S, D, E>, VB : ViewBinding, S : VROState, D : VRODestination, E : VROEvent> {

    var _binding: VB?

    val binding get() = _binding!!

    fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    fun setViewBindingObservers(viewModel: VM, binding: VB, fragment: Fragment) {
        fragment.viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                onViewUpdate(binding, it)
            }
        }
        viewModel.errorState.observe(fragment) {
            binding.onError(it)
        }
    }

    fun VB.onViewStarted()

    fun onViewUpdate(binding: VB, data: S)

    fun VB.onError(error: Throwable)

    fun onCreateViewBindingVro(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): View {
        _binding = createViewBinding(inflater, container)
        return binding.root
    }
}