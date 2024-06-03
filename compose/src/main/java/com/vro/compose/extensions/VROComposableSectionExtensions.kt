package com.vro.compose.extensions

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vro.compose.VROSection
import com.vro.event.VROEvent
import com.vro.event.VROEventListener
import com.vro.state.VROState

@Composable
internal fun <S : VROState, E : VROEvent> VroComposableSectionContainer(
    modifier: Modifier,
    state: S,
    eventListener: VROEventListener<E>,
    sectionList: List<VROSection<S, E>>,
) {
    Column(modifier = modifier) {
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
    modifier: Modifier,
    contentList: List<VROSection<S, E>>,
) {
    Column(modifier = modifier) {
        contentList.forEach {
            it.CreatePreview()
        }
    }
}