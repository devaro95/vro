package com.vro.fragment

import android.os.Bundle
import android.view.*
import androidx.annotation.CallSuper
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.navigation.VROFragmentNavigator.Companion.NAVIGATION_STATE
import com.vro.state.VROState

abstract class VROFragment<
        VM : VROViewModel<S, D, E>,
        S : VROState,
        VB : ViewBinding,
        D : VRODestination,
        E : VROEvent>
    : VROInjectionFragment<VM>(),
    VROFragmentBuilder<VM, S, D, E>,
    VROBindingFragment<VM, VB, S, D, E> {

    override val state: S? by lazy { restoreArguments() }

    @Suppress("UNCHECKED_CAST")
    private fun restoreArguments(): S? = arguments?.getSerializable(NAVIGATION_STATE) as? S

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateVro(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return onCreateViewBindingVro(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreatedVro(viewModel, findNavController(), viewLifecycleOwner)
        binding.onViewStarted()
    }

    override fun onPause() {
        super.onPause()
        onPauseVro(viewModel, this)
    }

    override fun onResume() {
        super.onResume()
        onResumeVro(viewModel, this)
        setViewBindingObservers(viewModel, binding, this)
    }
}