package com.vro.dialog

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.vro.core_android.viewmodel.VROViewModelFactory
import com.vro.event.VROEvent
import com.vro.state.VROStepper
import com.vro.state.VROStepper.VROStateStep
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.scope.createScope
import org.koin.core.component.KoinScopeComponent
import org.koin.core.scope.Scope

abstract class VROStateDialog<S : VRODialogState, VB : ViewBinding, VM : VRODialogViewModel<S, E>, E : VROEvent>
    : VRODialogBasics<S, VB>(), KoinScopeComponent {

    override val scope: Scope by lazy { createScope(this) }

    abstract val viewModelSeed: VM

    val viewModel by lazy {
        ViewModelProvider(this, VROViewModelFactory(viewModelSeed))[viewModelSeed.javaClass]
    }

    override fun initialize(binding: VB, state: S) {
        binding.onLoadFirstTime(state)
        viewModel.setInitialState(state)
        setObservers()
        viewModel.onStart()
    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.stepper.collectLatest { stepper ->
                when (stepper) {
                    is VROStepper.VROErrorStep -> binding!!.onError(stepper.error)
                    is VROStateStep -> onViewUpdate(binding!!, stepper.state)
                    else -> Unit
                }
            }
        }
    }

    abstract fun VB.onError(error: Throwable)

    fun event(event: E) {
        viewModel.eventListener(event)
    }
}