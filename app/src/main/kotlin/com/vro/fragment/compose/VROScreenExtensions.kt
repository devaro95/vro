package com.vro.fragment.compose

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vro.fragment.VROViewModel
import com.vro.fragment.VROViewModelFactory

object VROScreenExtensions {

    @Composable
    fun <VM: VROViewModel<*,*,*>> createViewModel(viewModelSeed: VM): VM {
        return viewModel(
            viewModelSeed::class.java,
            factory = VROViewModelFactory(viewModelSeed)
        )
    }
}