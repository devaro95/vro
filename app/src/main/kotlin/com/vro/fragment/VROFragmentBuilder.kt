package com.vro.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.vro.dialog.VRODialogState
import com.vro.event.VROEvent
import com.vro.navigation.VRODestination
import com.vro.navigation.VROFragmentNavigator.Companion.NAVIGATION_BACK_STATE
import com.vro.navigation.VRONavigator
import com.vro.state.VROState
import kotlinx.coroutines.launch
import java.io.Serializable

interface VROFragmentBuilder<VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> {

    val navigator: VRONavigator<D>

    val state: S?

    fun initializeState(viewModel: VM, fragment: Fragment) {
        setObservers(viewModel, fragment)
        viewModel.createInitialState()
        viewModel.setInitialState(state)
    }

    fun onViewCreatedVro(viewModel: VM, navController: NavController, viewLifecycleOwner: LifecycleOwner) {
        getNavigationResult(navController)?.observe(viewLifecycleOwner) { result ->
            result?.let { viewModel.setOnResult(it) }
        }
    }

    fun onResumeVro(viewModel: VM) {
        viewModel.onResume()
    }

    private fun setObservers(viewModel: VM, fragment: Fragment) {
        fragment.lifecycleScope.launch {
            viewModel.dialogState.observe(fragment) {
                onLoadDialog(it)
            }
        }
        viewModel.navigationState.observe(fragment) {
            if (it.navigateBack) navigator.navigateBack(it.backResult)
            else it.destination?.let { destination -> navigator.navigate(destination) }
        }
    }

    fun onLoadDialog(data: VRODialogState)

    private fun getNavigationResult(navController: NavController) =
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Serializable>(NAVIGATION_BACK_STATE)
}