package com.vro.fragment

import android.os.Bundle
import android.view.*
import androidx.annotation.CallSuper
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.vro.event.VROEvent
import com.vro.navigation.VROBackResult
import com.vro.navigation.VRODestination
import com.vro.navigation.VROFragmentNavigator.Companion.NAVIGATION_STATE
import com.vro.state.VROState
import com.vro.state.VROStepper.VROStateStep
import com.vro.viewmodel.VROViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class VROFragment<
        VM : VROViewModel<S, D, E>,
        S : VROState,
        VB : ViewBinding,
        D : VRODestination,
        E : VROEvent,
        > : VROInjectionFragment<VM>(), VROFragmentBuilder<VM, S, D, E> {

    private var _binding: VB? = null

    val binding get() = _binding!!

    override val state: S? by lazy { restoreArguments() }

    abstract fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    abstract fun VB.onViewStarted()

    abstract fun onViewUpdate(binding: VB, data: S)

    abstract fun VB.onError(error: Throwable)

    private fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): View {
        _binding = createViewBinding(inflater, container)
        return binding.root
    }

    private fun setViewBindingObservers() {
        lifecycleScope.launch {
            viewModel.stepper.collectLatest { stepper ->
                if (stepper is VROStateStep<S>) {
                    onViewUpdate(binding, stepper.state)
                }
            }
        }
        lifecycleScope.launch {
            viewModel.errorState.collect {
                binding.onError(it)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun restoreArguments(): S? = arguments?.getSerializable(NAVIGATION_STATE) as? S

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeState(viewModel, this)
        viewModel.onStart()
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

    override fun onResume() {
        super.onResume()
        onResumeVro(viewModel)
        setViewBindingObservers()
    }

    fun event(event: E) {
        viewModel.eventListener(event)
    }

    fun navigateBack(result: VROBackResult?) {
        viewModel.eventBack(result)
    }
}