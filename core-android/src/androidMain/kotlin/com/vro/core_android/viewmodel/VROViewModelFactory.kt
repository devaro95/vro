package com.vro.core_android.viewmodel

import androidx.lifecycle.*
import com.vro.core_android.factory.VROFactory
import com.vro.viewmodel.VRODialogViewModel
import com.vro.viewmodel.VROViewModel
import com.vro.viewmodel.VROViewModelCore
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState

@Suppress("UNCHECKED_CAST")
class VRODialogViewModelFactory(val viewModel: VROViewModelCore<*, *>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModel as T
}

class VROViewModelFactory<VM : VROViewModel<*, *, *>>(
    private val viewModel: VM,
) : VROFactory<VM>() {
    override fun createViewModel(): VM = viewModel
}
