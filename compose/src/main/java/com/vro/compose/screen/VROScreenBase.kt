package com.vro.compose.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.vro.compose.preview.VROLightMultiDevicePreview
import com.vro.compose.skeleton.VROSkeleton
import com.vro.compose.states.*
import com.vro.compose.states.VROBottomBarBaseState.VROBottomBarStartState
import com.vro.compose.states.VROTopBarBaseState.VROTopBarStartState
import com.vro.compose.template.VROTemplate
import com.vro.compose.utils.isTablet
import com.vro.core_android.di.vroScopeName
import com.vro.event.VROEvent
import com.vro.event.VROEventLauncher
import com.vro.navigation.VROBackResult
import com.vro.state.VRODialogData
import com.vro.state.VROState
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinScopeComponent
import org.koin.core.context.GlobalContext.get
import org.koin.core.qualifier.named
import java.util.UUID
import kotlin.reflect.KClass

abstract class VROScreenBase<S : VROState, E : VROEvent> : KoinScopeComponent {

    override val scope = getKoin().createScope(UUID.randomUUID().toString(), vroScopeName(this::class))

    lateinit var navController: NavController

    open val skeleton: VROSkeleton? = null

    lateinit var events: VROEventLauncher<E>

    open val tabletDesignEnabled: Boolean = false

    internal lateinit var screenState: S

    internal lateinit var topBarState: MutableState<VROTopBarBaseState>

    internal lateinit var bottomBarState: MutableState<VROBottomBarBaseState>

    lateinit var snackbarState: MutableState<VROSnackBarState>

    open fun setTopBar(currentState: VROTopBarBaseState): VROTopBarBaseState = VROTopBarStartState()

    open fun setBottomBar(currentState: VROBottomBarBaseState): VROBottomBarBaseState = VROBottomBarStartState()

    lateinit var coroutineScope: CoroutineScope

    @Composable
    internal fun ComposableScreenSkeleton() {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            skeleton?.SkeletonContent()
        }
    }

    @Composable
    internal fun ComposableScreenContainer(
        state: S,
        topBarState: MutableState<VROTopBarBaseState>,
        bottomBarState: MutableState<VROBottomBarBaseState>,
        snackbarState: MutableState<VROSnackBarState>,
    ) {
        this.topBarState = topBarState
        this.bottomBarState = bottomBarState
        this.snackbarState = snackbarState
        screenState = state
        coroutineScope = rememberCoroutineScope()
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (isTablet() && tabletDesignEnabled) {
                ScreenTabletContent(state)
            } else {
                ScreenContent(state)
            }
        }
    }

    @Composable
    fun AddTemplate(templateClass: KClass<out VROTemplate<*, *, *, *, *, *>>) {
        val template: VROTemplate<*, *, *, *, *, *> = scope.get(templateClass)
        template.ComposableTemplateContainer(navController)
    }

    @Composable
    abstract fun ScreenContent(state: S)

    @Composable
    open fun ScreenTabletContent(state: S) = Unit

    @VROLightMultiDevicePreview
    @Composable
    abstract fun ScreenPreview()

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

    fun navigateBack(result: VROBackResult? = null) {
        events.doBack(result)
    }
}