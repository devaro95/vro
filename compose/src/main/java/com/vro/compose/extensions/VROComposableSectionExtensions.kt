package com.vro.compose.extensions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.vro.compose.VROComposableSection
import com.vro.compose.model.VROOrientation
import com.vro.compose.utils.isTablet
import com.vro.event.VROEvent
import com.vro.state.VROState

@Composable
internal fun <S : VROState, E : VROEvent> VroComposableSectionContainer(
    state: S,
    eventLauncher: E,
    orientation: VROOrientation,
    sectionList: List<VROComposableSection<S, E>>,
) {
    if (isTablet() || orientation == VROOrientation.HORIZONTAL)
        Row {
            sectionList.forEach {
                VroComposableSection(state, eventLauncher, it)
            }
        }
    else {
        Column {
            sectionList.forEach {
                VroComposableSection(state, eventLauncher, it)
            }
        }
    }
}

@Composable
internal fun <S : VROState, E : VROEvent> VroComposableSection(
    state: S,
    eventLauncher: E,
    content: VROComposableSection<S, E>,
) {
    content.CreateSection(state)
    content.eventLauncher = eventLauncher
}


@Composable
internal fun <S : VROState, E : VROEvent> VroComposablePreview(
    contentList: List<VROComposableSection<S, E>>,
) {
    Column {
        contentList.forEach {
            it.CreatePreview()
        }
    }
}