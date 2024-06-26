package com.vro.testing

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vro.event.VROEvent
import com.vro.event.VROEventListener
import com.vro.fragment.VROViewModel
import com.vro.navigation.VRODestination
import com.vro.navigation.VRONavigationState
import com.vro.state.VROState
import com.vro.state.VROStepper
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.MockitoAnnotations
import org.mockito.exceptions.misusing.MissingMethodInvocationException
import java.io.Serializable
import kotlin.reflect.KClass

abstract class VROViewModelTest<S : VROState, VM : VROViewModel<S, *, E>, E : VROEvent> {

    lateinit var viewModel: VM

    private lateinit var eventLauncher: VROEventListener<E>

    fun event(event: E) {
        eventLauncher.eventListener(event)
    }

    @Before
    open fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = onSetupViewModel()
        viewModel.concurrencyManager = VROTestDefaultConcurrencyManager()
        eventLauncher = viewModel
    }

    abstract fun onSetupViewModel(): VM

    abstract fun onSetupInitialState(): S

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    protected fun getDataState(): S {
        return viewModel.checkDataState()
    }

    fun verifyDialog(dialogType: Int) {
        runTest {
            viewModel.stepper.first().let {
                assertEquals((it as VROStepper.VRODialogStep<S>).dialogState.type, dialogType)
            }
        }
    }

    fun verifyUpdateScreen() {
        runTest {
            withTimeoutOrNull(5000) {
                viewModel.stepper.firstOrNull()?.let {
                    assertEquals(it::class.java, VROStepper.VROStateStep::class.java)
                }
            } ?: throw MissingMethodInvocationException("updateScreen not being called")
        }
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

    fun verifyNavigateBack(result: Serializable? = null) {
        assertNull(getNavigatorState()?.destination)
        assertEquals(getNavigatorState()?.backResult, result)
    }
}
