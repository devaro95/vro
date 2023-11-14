package com.vro.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.vro.dialog.VRODialogState

abstract class VROActivity<VB : ViewBinding, VM : VROActivityViewModel> : VROInjectionActivity<VM>() {

    abstract fun createViewBinding(inflater: LayoutInflater): VB

    open lateinit var activityBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStateObserver(viewModel)
        createViewBinding(layoutInflater).let {
            activityBinding = it
            setContentView(it.root)
        }
        onCreated(activityBinding)
    }

    private fun setStateObserver(viewModel: VM) {
        viewModel.dialogState.observe(this) {
            onLoadDialog(it)
        }
    }

    fun onLoadDialog(data: VRODialogState) = Unit

    @CallSuper
    abstract fun onCreated(binding: VB)

}