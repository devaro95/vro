package com.vro.testing

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vro.event.VROEvent
import com.vro.fragment.VROViewModel
import com.vro.navigation.VRODestination
import com.vro.navigation.VRONavigationState
import com.vro.state.VRODialogState
import com.vro.state.VROState
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.MockitoAnnotations
import java.io.Serializable
import kotlin.reflect.KClass

abstract class VROViewModelTest<S : VROState, VM : VROViewModel<S, *, E>, E : VROEvent> {

    protected val viewModel: VM by lazy {
        onSetupViewModel()
    }

    @Before
    open fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel.concurrencyManager = VROTestDefaultConcurrencyManager()
        viewModel.createInitialState()
    }

    abstract fun onSetupViewModel(): VM

    abstract fun onSetupInitialState(): S

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    protected fun getDataState(): S {
        return viewModel.checkDataState()
    }

    protected fun getDialogState(): VRODialogState? {
        return viewModel.dialogState.value
    }

    private fun getNavigatorState(): VRONavigationState<out VRODestination>? {
        return viewModel.navigationState.value
    }

    fun verifyNavigation(destination: KClass<*>) {
        getNavigatorState()?.destination?.let {
            assertEquals(it::class, destination)
        }
    }

    fun verifyNoNavigation(destination: KClass<*>) {
        getNavigatorState()?.destination?.let {
            assertNotEquals(it::class, destination)
        }
    }


    fun verifyNavigation(destination: VRODestination) {
        getNavigatorState()?.destination?.let {
            assertEquals(it, destination)
        }
    }

    fun verifyDialog(dialogType: Int) {
        assertEquals(getDialogState()?.type, dialogType)
    }

    fun verifyNavigateBack(result: Serializable? = null) {
        assertNull(getNavigatorState()?.destination)
        assertEquals(getNavigatorState()?.backResult, result)
    }

    fun viewModelEvent(event: E) {
        viewModel.eventListener(event)
    }
}
