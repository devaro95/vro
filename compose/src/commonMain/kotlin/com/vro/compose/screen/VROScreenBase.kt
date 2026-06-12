package com.vro.compose.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.vro.compose.handler.*
import com.vro.compose.handler.default.*
import com.vro.compose.navigator.VRONavigationClass
import com.vro.compose.skeleton.VROSkeleton
import com.vro.compose.skeleton.VROSkeletonDefault
import com.vro.compose.states.*
import com.vro.event.VROEvent
import com.vro.event.VROEventLauncher
import com.vro.state.VROState
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope

abstract class VROScreenBase<S : VROState, E : VROEvent>(
    open val dialogHandler: VRODialogHandler<E> = VRODialogHandlerDefault(),
    open val errorHandler: VROErrorHandler<E> = VROErrorHandlerDefault(),
    open val oneTimeHandler: VROOneTimeHandler<S, E> = VROOneTimeHandlerDefault(),
    open val skeleton: VROSkeleton = VROSkeletonDefault(),
) : VRONavigationClass, KoinScopeComponent {

    override val scope: Scope by lazy { createScope(this) }

    open val tabletDesignEnabled: Boolean = false

    internal lateinit var screenState: S

    internal val isStarted: MutableState<Boolean> = mutableStateOf(false)

    @Composable
    internal fun ComposableScreenContainer(
        state: S,
        events: VROEventLauncher<E>
    ) {
        InitializeState(state = state)
        InitializeHandlers(events = events)
        InitializeContent(state = state)
    }

    @Composable
    fun InitializeState(state: S) {
        screenState = state
    }

    @Composable
    fun InitializeHandlers(events: VROEventLauncher<E>) {
        dialogHandler.events = events
        errorHandler.events = events
        oneTimeHandler.events = events
    }

    @Composable
    abstract fun InitializeContent(state: S)

    @Composable
    abstract fun InitializeEvents(events: VROEventLauncher<E>)

    @Composable
    abstract fun InitializeBars()

    @Composable
    open fun TabletContent(state: S) = Unit
}
