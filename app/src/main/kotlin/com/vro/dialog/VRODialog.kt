package com.vro.dialog

import androidx.viewbinding.ViewBinding

abstract class VRODialog<S : VRODialogState, VB : ViewBinding> : VRODialogBasics<S, VB>() {

    override fun initialize(binding: VB, state: S) {
        binding.onLoadFirstTime(state)
        onStateSetup(state)
    }

    private fun onStateSetup(state: S) {
        (state as? S)?.let { state ->
            binding?.let { binding ->
                onViewUpdate(binding, state)
            }
        } ?: throw Exception("State type not matching with the dialog")
    }
}