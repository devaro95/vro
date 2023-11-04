package com.vro.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.vro.dialog.VRODialogState
import com.vro.fragment.compose.VROEvent
import com.vro.navigation.VRODestination
import com.vro.navigation.VROFragmentNavigator.Companion.NAVIGATION_BACK_STATE
import com.vro.navigation.VRONavigator
import com.vro.state.VROState
import java.io.Serializable

interface VROFragmentBuilder<VM : VROViewModel<S, D, E>, S : VROState, D : VRODestination, E : VROEvent> {

    val state: S?

    val navigator: VRONavigator<D>

    fun onCreateVro(viewModel: VM) {
        viewModel.onInitializeState()
        viewModel.setInitialState(state)
    }

    fun onViewCreatedVro(viewModel: VM, navController: NavController, viewLifecycleOwner: LifecycleOwner) {
        viewModel.onStart()
        getNavigationResult(navController)?.observe(viewLifecycleOwner) { result ->
            result?.let { viewModel.setOnResult(it) }
        }
    }

    fun onResumeVro(viewModel: VM, fragment: Fragment) {
        viewModel.onResume()
        setStateObserver(viewModel, fragment)
    }

    private fun setStateObserver(viewModel: VM, fragment: Fragment) {
        viewModel.dialogState.observe(fragment) {
            onLoadDialog(it)
        }
        viewModel.navigationState.observe(fragment) {
            if (it.navigateBack) navigator.navigateBack(it.backResult)
            else it.destination?.let { destination -> navigator.navigate(destination) }
        }
    }

    fun onLoadDialog(data: VRODialogState)

    fun onPauseVro(viewModel: VM, fragment: Fragment) {
        viewModel.unBindObservables(fragment)
    }

    private fun getNavigationResult(navController: NavController) =
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Serializable>(NAVIGATION_BACK_STATE)
}