@file:Suppress("UNCHECKED_CAST")

package com.vro.core_android.viewmodel

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.state.VROState
import com.vro.viewmodel.VRODialogViewModel
import com.vro.viewmodel.VROViewModel

fun <S : VROState, D : VRODestination, E : VROEvent> initializeViewModel(
    activity: AppCompatActivity,
    viewModelSeed: VROViewModel<S, D, E>,
): VROAndroidViewModel<S, D, E> {
    val vroFactory = VROViewModelFactory(viewModelSeed)
    val vm = ViewModelProvider(
        activity,
        vroFactory
    )[viewModelSeed.id, VROAndroidViewModel::class.java]
    return vm as VROAndroidViewModel<S, D, E>
}

fun <S : VROState, D : VRODestination, E : VROEvent> initializeViewModel(
    fragment: Fragment,
    viewModelSeed: VROViewModel<S, D, E>,
): VROAndroidViewModel<S, D, E> {
    val vroFactory = VROViewModelFactory(viewModelSeed)
    val vm = ViewModelProvider(
        fragment,
        vroFactory
    )[viewModelSeed.id, VROAndroidViewModel::class.java]
    return vm as VROAndroidViewModel<S, D, E>
}

fun <S : VROState, D : VRODestination, E : VROEvent> initializeViewModel(
    dialogFragment: DialogFragment,
    viewModelSeed: VRODialogViewModel<S, E>,
): VROAndroidViewModel<S, D, E> {
    val vroFactory = VRODialogViewModelFactory(viewModelSeed)
    val vm = ViewModelProvider(
        dialogFragment,
        vroFactory
    )[viewModelSeed.id, VROAndroidViewModel::class.java]
    return vm as VROAndroidViewModel<S, D, E>
}