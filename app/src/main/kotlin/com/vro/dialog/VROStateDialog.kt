package com.vro.dialog

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.vro.core_android.viewmodel.VROAndroidDialogViewModel
import com.vro.core_android.viewmodel.VRODialogViewModelFactory
import com.vro.event.VROEvent
import com.vro.state.VROStepper
import com.vro.state.VROStepper.VROStateStep
import com.vro.viewmodel.VRODialogViewModel
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
        val vroFactory = VRODialogViewModelFactory(viewModelSeed)
        ViewModelProvider(
            this,
            vroFactory
        )[viewModelSeed.id, VROAndroidDialogViewModel::class.java]
    }

    override fun initialize(binding: VB, state: S) {
        binding.onLoadFirstTime(state)
        (viewModel.vroViewModel as VRODialogViewModel<S, E>).setInitialState(state)
        setObservers()
        (viewModel.vroViewModel as VRODialogViewModel<S, E>).onStart()
    }

    private fun setObservers() {
        lifecycleScope.launch {
            (viewModel.vroViewModel as VRODialogViewModel<S, E>).stepper.collectLatest { stepper ->
                when (stepper) {
                    is VROStepper.VROErrorStep -> binding!!.onError(stepper.error)
                    is VROStateStep -> onViewUpdate(binding!!, stepper.state)
                    else -> Unit
                }
            }
        }
    }

    abstract fun VB.onError(error: Throwable)
}