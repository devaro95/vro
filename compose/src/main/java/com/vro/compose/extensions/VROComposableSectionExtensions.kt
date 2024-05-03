package com.vro.compose.extensions

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.vro.compose.VROComposableViewModel
import com.vro.compose.VROSection
import com.vro.event.VROEvent
import com.vro.event.VROEventListener
import com.vro.navigation.VRODestination
import com.vro.state.VROState

@Composable
internal fun <S : VROState, E : VROEvent> VroComposableSectionContainer(
    state: S,
    eventListener: VROEventListener<E>,
    sectionList: List<VROSection<S, E>>,
) {
    Column {
        sectionList.forEach {
            VroComposableSection(state, eventListener, it)
        }
    }
}

@Composable
internal fun <S : VROState, E : VROEvent> VroComposableSection(
    state: S,
    eventListener: VROEventListener<E>,
    content: VROSection<S, E>,
) {
    content.eventListener = eventListener
    content.CreateSection(state)
}


@Composable
internal fun <S : VROState, E : VROEvent> VroComposablePreview(
    contentList: List<VROSection<S, E>>,
) {
    Column {
        contentList.forEach {
            it.CreatePreview()
        }
    }
}