package com.vro.fragment

import android.os.Bundle
import android.view.*
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.vro.core_android.fragment.VROFragmentInjection
import com.vro.core_android.navigation.VRONavigator.Companion.NAVIGATION_STATE
import com.vro.event.VROEvent
import com.vro.navigation.*
import com.vro.state.*
import com.vro.state.VROStepper.VROStateStep
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class VROFragment<
        VM : VROViewModel<S, D, E>,
        S : VROState,
        VB : ViewBinding,
        D : VRODestination,
        E : VROEvent,
        > : VROFragmentInjection<VM>(), VROFragmentBasics<VM, S, D, E> {

    private var _binding: VB? = null

    val binding get() = _binding!!

    override val state: S? by lazy { restoreArguments() }

    abstract fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    abstract fun VB.onViewStarted()

    abstract fun onViewUpdate(binding: VB, data: S)

    abstract fun VB.onError(error: Throwable)

    abstract fun VB.oneTimeHandler(id: Int, state: S)

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
                when (stepper) {
                    is VROStepper.VRODialogStep -> onLoadDialog(stepper.dialogState)
                    is VROStepper.VROErrorStep -> binding.onError(stepper.error)
                    is VROStateStep -> onViewUpdate(binding, stepper.state)
                    else -> Unit
                }
            }
        }
        lifecycleScope.launch {
            viewModel.getNavigationState().collect {
                it?.destination?.let { destination ->
                    if (!destination.isNavigated) {
                        navigator.navigate(destination)
                        destination.setNavigated()
                    }
                } ?: navigator.navigateBack(it?.backResult)
            }
        }
        lifecycleScope.launch {
            viewModel.getOneTimeEvents().collectLatest { oneTime ->
                if (oneTime is VROOneTimeState.Launch) {
                    binding.oneTimeHandler(oneTime.id, oneTime.state)
                    viewModel.clearOneTime()
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun restoreArguments(): S? = arguments?.getSerializable(NAVIGATION_STATE) as? S

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeState(viewModel, this)
        viewModel.onNavParam(getStarterParam(findNavController().currentDestination?.id.toString()))
        lifecycleScope.launch {
            viewModel.onStart()
        }
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
        getOnNavResult()
        binding.onViewStarted()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
        setViewBindingObservers()
    }

    fun event(event: E) {
        viewModel.eventListener(event)
    }

    fun navigateBack(result: VROBackResult?) {
        viewModel.eventBack(result)
    }

    private fun initializeState(viewModel: VM, fragment: Fragment) {
        setObservers(viewModel, fragment)
        viewModel.setInitialState(state)
    }

    private fun getOnNavResult() {
        getResultParam(findNavController().currentDestination?.id.toString())?.let {
            viewModel.onNavResult(it)
        }
    }
}