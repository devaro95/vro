package com.vro.core_android.factory

import androidx.lifecycle.*
import com.vro.core_android.viewmodel.VROAndroidViewModel
import com.vro.core_android.viewmodel.VROViewModel

abstract class VROFactory<VM : VROViewModel<*, *, *>> : AbstractSavedStateViewModelFactory() {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle,
    ): T {
        return VROAndroidViewModel(createViewModel()) as T
    }

    abstract fun createViewModel(): VM
}