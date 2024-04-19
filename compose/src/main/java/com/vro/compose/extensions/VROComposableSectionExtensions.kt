package com.vro.compose.extensions

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.vro.compose.VROSection
import com.vro.event.VROEvent
import com.vro.state.VROState

@Composable
internal fun <S : VROState, E : VROEvent> VroComposableSectionContainer(
    state: S,
    eventLauncher: E,
    sectionList: List<VROSection<S, E>>,
) {
    Column {
        sectionList.forEach {
            VroComposableSection(state, eventLauncher, it)
        }
    }
}

@Composable
internal fun <S : VROState, E : VROEvent> VroComposableSection(
    state: S,
    eventLauncher: E,
    content: VROSection<S, E>,
) {
    content.CreateSection(state)
    content.eventLauncher = eventLauncher
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