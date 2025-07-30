package com.vro.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.CallSuper
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.vro.core_android.navigation.VRONavigator
import com.vro.event.VROEvent
import com.vro.viewmodel.VROViewModel
import com.vro.navigation.VRODestination
import com.vro.state.VRODialogData
import com.vro.state.VROState
import com.vro.state.VROStepper.VRODialogStep
import com.vro.state.VROStepper.VROErrorStep
import com.vro.state.VROStepper.VROStateStep
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class VROActivity<
        S : VROState,
        VB : ViewBinding,
        VM : VROViewModel<S, D, E>,
        D : VRODestination,
        E : VROEvent,
        > : VROActivityInjection<S, VM, D, E>() {

    abstract fun createViewBinding(inflater: LayoutInflater): VB

    open lateinit var activityBinding: VB

    abstract val navigator: VRONavigator<D>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createViewBinding(layoutInflater).also {
            activityBinding = it
            setContentView(it.root)
        }
        onCreated(activityBinding)
    }

    private fun setViewBindingObservers() {
        lifecycleScope.launch {
            vm.vroViewModel.stepper.collectLatest { stepper ->
                when (stepper) {
                    is VROStateStep -> onViewUpdate(activityBinding, stepper.state)
                    is VRODialogStep -> onLoadDialog(stepper.dialogState)
                    is VROErrorStep -> activityBinding.onError(stepper.error)
                    else -> Unit
                }
            }
        }

        lifecycleScope.launch {
            vm.vroViewModel.getNavigationState().collect {
                it?.destination?.let { destination ->
                    if (!destination.isNavigated) {
                        navigator.navigate(destination)
                        destination.setNavigated()
                    }
                } ?: navigator.navigateBack(it?.backResult)
            }
        }
    }

    abstract fun VB.onError(error: Throwable)

    abstract fun onLoadDialog(data: VRODialogData)

    abstract fun onViewUpdate(binding: VB, data: S)

    override fun onResume() {
        super.onResume()
        setViewBindingObservers()
    }

    @CallSuper
    abstract fun onCreated(binding: VB)

}