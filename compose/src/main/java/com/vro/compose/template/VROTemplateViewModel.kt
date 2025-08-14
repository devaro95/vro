package com.vro.compose.template

import com.vro.viewmodel.VROViewModelNav
import com.vro.event.VROEvent
import com.vro.navigation.VROBackResult
import com.vro.navigation.VRODestination
import com.vro.state.VROState

/**
 * Abstract base ViewModel class for screens built using [VROTemplate].
 *
 * This ViewModel follows a state-event-navigation pattern and is responsible for managing:
 * - The current UI [VROState]
 * - Navigation to [VRODestination] targets
 * - Handling UI and business [VROEvent]s
 * - Optional [VROBackResult] when navigating back
 *
 * This class should be extended to implement screen-specific logic, exposing state flows
 * and reacting to incoming events via standard MVI or MVVM patterns.
 *
 * @param S The type of state associated with the screen, must extend [VROState].
 * @param D The type of navigation destination, must extend [VRODestination].
 * @param E The type of events, must extend [VROEvent].
 */
abstract class VROTemplateViewModel<S : VROState, D : VRODestination, E : VROEvent> : VROViewModelNav<S, D, E>(){
}