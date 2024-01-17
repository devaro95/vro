package com.vro.event

import java.io.Serializable

interface VROEventLauncher<E : VROEvent> {
    fun launchEvent(event: E)
    fun navigateBack(result: Serializable? = null)
}