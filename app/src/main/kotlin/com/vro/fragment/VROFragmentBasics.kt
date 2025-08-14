package com.vro.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.vro.core_android.navigation.VRONavigator
import com.vro.viewmodel.VROViewModel
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

interface VROFragmentBasics<VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> {

    val navigator: VRONavigator<D>

    val state: S?

    fun onLoadDialog(data: VRODialogData)
}