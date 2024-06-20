package com.vro.compose.extensions

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vro.fragment.VROViewModelFactory
import com.vro.compose.viewmodel.VROComposableViewModel
import com.vro.viewmodel.VROViewModel

@Composable
fun <VM : VROComposableViewModel<*, *, *>> createViewModel(viewModelSeed: VM): VM {
    return viewModel(
        viewModelSeed::class.java,
        factory = VROViewModelFactory(viewModelSeed)
    )
}

@Composable
fun <VM : VROViewModel<*, *, *>> createViewModel(viewModelSeed: VM): VM {
    return viewModel(
        viewModelSeed::class.java,
        factory = VROViewModelFactory(viewModelSeed)
    )
}