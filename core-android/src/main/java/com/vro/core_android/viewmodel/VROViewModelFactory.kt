package com.vro.core_android.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vro.core_android.factory.VROFactory

@Suppress("UNCHECKED_CAST")
class VRODialogViewModelFactory(val viewModel: VROViewModelCore<*, *>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModel as T
    }
}
class VROViewModelFactory<VM : VROViewModel<*, *, *>>(
    private val viewModel: VM,
) : VROFactory<VM>() {
    override fun createViewModel(): VM {
        return viewModel
    }
}
