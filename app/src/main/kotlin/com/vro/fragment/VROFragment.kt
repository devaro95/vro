package com.vro.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState

abstract class VROFragment<
        VM : VROViewModel<S, D, E>,
        S : VROState,
        VB : ViewBinding,
        D : VRODestination,
        E : VROEvent,
        > : VROBindingFragment<VM, VB, S, D, E>(), VROFragmentBuilder<VM, S, D, E> {

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeState()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return createBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreatedVro(viewModel, findNavController(), viewLifecycleOwner)
        binding.onViewStarted()
    }

    override fun onPause() {
        super.onPause()
        unbindObservers(viewModel, this)
    }

    override fun onResume() {
        super.onResume()
        onResumeVro(viewModel, this)
        setViewBindingObservers()
    }

    fun viewModelEvent(event: E) {
        viewModel.eventListener(event)
    }
}