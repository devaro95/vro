package com.vro.compose.template

import android.annotation.SuppressLint
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.vro.compose.VROComposableActivity
import com.vro.compose.VROComposableNavigator
import com.vro.compose.initializers.*
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.skeleton.VROSkeleton
import com.vro.event.VROEvent
import com.vro.event.VROEventLauncher
import com.vro.navigation.VRODestination
import com.vro.state.VRODialogData
import com.vro.state.VROState
import org.koin.core.component.KoinScopeComponent
import org.koin.core.context.GlobalContext.get
import org.koin.core.qualifier.named

abstract class VROTemplate<
        VM : VROTemplateViewModel<S, D, E>,
        S : VROState,
        D : VRODestination,
        E : VROEvent,
        M : VROTemplateMapper,
        R : VROTemplateRender<E, S>,
        > : KoinScopeComponent {

    override val scope = get().getScopeOrNull(this::class.toString()) ?: get().createScope(this::class.toString(), named(this::class.toString()))

    lateinit var events: VROEventLauncher<E>

    abstract val viewModel: VM

    lateinit var activity: VROComposableActivity

    lateinit var navController: NavController

    abstract fun createNavigator(): VROComposableNavigator<D>

    open val skeleton: VROSkeleton? = null

    abstract fun mapper(): M

    @Composable
    abstract fun render(state: S): R

    @Composable
    internal fun ComposableTemplateContainer(navController: NavController) {
        this.activity = LocalActivity.current as VROComposableActivity
        this.events = viewModel
        this.navController = navController
        val lifecycle = LocalLifecycleOwner.current.lifecycle
        InitializeLifecycleObserver(
            viewModel = viewModel,
            lifecycle = lifecycle
        )
        InitializeNavigatorListener(
            viewModel = viewModel,
            navigator = createNavigator()
        )
        InitializeOneTimeListener(
            viewModel = viewModel,
            content = this
        )
        InitializeStepperListener(
            viewModel = viewModel,
            content = this,
            lifecycle = lifecycle
        )
        InitializeEventsListener(
            viewModel = viewModel
        )
    }

    @Composable
    internal fun TemplateScreenSkeleton() {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            skeleton?.SkeletonContent()
        }
    }

    @Composable
    abstract fun TemplateContent(state: S)

    @VROLightMultiDevicePreview
    @Composable
    abstract fun TemplatePreview()

    @SuppressLint("ComposableNaming")
    @Composable
    open fun onDialog(data: VRODialogData) = Unit

    @SuppressLint("ComposableNaming")
    @Composable
    open fun onError(error: Throwable, data: Any?) = Unit

    open fun oneTimeHandler(id: Int, state: S) = Unit

    fun event(event: E) {
        events.doEvent(event)
    }
}