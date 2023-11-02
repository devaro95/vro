package com.vro.fragment

import android.os.Bundle
import android.view.*
import androidx.annotation.CallSuper
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.vro.dialog.VRODialogState
import com.vro.navigation.VRODestination
import com.vro.navigation.VRONavigator
import com.vro.state.VROState
import java.io.Serializable

abstract class VROFragment<VM : VROViewModel<S, D>, S : VROState, VB : ViewBinding, D : VRODestination> : VROInjectionFragment<VM>() {

    private val state: S? by lazy { restoreArguments() }

    private var _binding: VB? = null

    protected val binding get() = _binding!!

    abstract val navigator: VRONavigator<D>

    @Suppress("UNCHECKED_CAST")
    private fun restoreArguments(): S? = arguments?.getSerializable(NAVIGATION_STATE) as? S

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onInitializeState()
        viewModel.setInitialState(state)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = createViewBinding(inflater, container)
        return binding.root
    }

    abstract fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onStart()
        getNavigationResult()?.observe(viewLifecycleOwner) { result ->
            result?.let { viewModel.setOnResult(it) }
        }
        binding.onViewStarted()
    }

    private fun setStateObserver() {
        viewModel.state.observe(this) {
            onViewUpdate(binding, it)
        }
        viewModel.dialogState.observe(this) {
            onLoadDialog(it)
        }
        viewModel.errorState.observe(this) {
            binding.onError(it)
        }
        viewModel.navigationState.observe(this) {
            if (it.navigateBack) navigator.navigateBack(it.backResult)
            else it.destination?.let { destination -> navigator.navigate(destination) }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.unBindObservables(this)
    }

    abstract fun VB.onViewStarted()

    abstract fun onViewUpdate(binding: VB, data: S)

    abstract fun onLoadDialog(data: VRODialogState)

    abstract fun VB.onError(error: Throwable)

    companion object {
        const val NAVIGATION_STATE = "NAVIGATION_STATE"
        const val NAVIGATION_BACK_STATE = "NAVIGATION_BACK_STATE"
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
        setStateObserver()
    }

    private fun getNavigationResult() =
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Serializable>(NAVIGATION_BACK_STATE)
}