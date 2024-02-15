package com.vro.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.CallSuper
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.vro.event.VROEvent
import com.vro.fragment.VROViewModel
import com.vro.navigation.VRODestination
import com.vro.navigation.VRONavigator
import com.vro.state.VRODialogState
import com.vro.state.VROState
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
        viewModel.createInitialState()
        onCreated(activityBinding)
    }

    private fun setViewBindingObservers() {
        lifecycleScope.launch {
            viewModel.state.collectLatest {
                onViewUpdate(activityBinding, it)
            }
            viewModel.dialogState.observe(this@VROActivity) {
                onLoadDialog(it)
            }
        }

        viewModel.errorState.observe(this) {
            activityBinding.onError(it)
        }

        viewModel.navigationState.observe(this) {
            if (it.navigateBack) navigator.navigateBack(it.backResult)
            else it.destination?.let { destination -> navigator.navigate(destination) }
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