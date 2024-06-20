package com.vro.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.CallSuper
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.navigation.VRONavigator
import com.vro.state.VRODialogState
import com.vro.state.VROState
import com.vro.state.VROStepper.VRODialogStep
import com.vro.state.VROStepper.VROStateStep
import com.vro.viewmodel.VROViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class VROActivity<
        S : VROState,
        VB : ViewBinding,
        VM : VROViewModel<S, D, E>,
        D : VRODestination,
        E : VROEvent,
        > : VROInjectionActivity<S, VM, D, E>() {

    abstract fun createViewBinding(inflater: LayoutInflater): VB

    open lateinit var activityBinding: VB

    abstract val navigator: VRONavigator<D>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createViewBinding(layoutInflater).let {
            activityBinding = it
            setContentView(it.root)
        }
        onCreated(activityBinding)
    }

    private fun setViewBindingObservers() {
        lifecycleScope.launch {
            launch {
                viewModel.stepper.collectLatest { stepper ->
                    when (stepper) {
                        is VROStateStep -> onViewUpdate(activityBinding, stepper.state)
                        is VRODialogStep -> onLoadDialog(stepper.dialogState)
                    }
                }
            }
            launch {
                viewModel.navigationState.collectLatest {
                    it?.destination?.let { destination ->
                        if (!destination.isNavigated) {
                            navigator.navigate(destination)
                            destination.setNavigated()
                        }
                    } ?: navigator.navigateBack(it?.backResult)
                }
            }
            launch {
                viewModel.errorState.collectLatest {
                    activityBinding.onError(it)
                }
            }
        }
    }

    abstract fun VB.onError(error: Throwable)

    abstract fun onLoadDialog(data: VRODialogState)

    abstract fun onViewUpdate(binding: VB, data: S)

    override fun onResume() {
        super.onResume()
        setViewBindingObservers()
    }

    @CallSuper
    abstract fun onCreated(binding: VB)

}