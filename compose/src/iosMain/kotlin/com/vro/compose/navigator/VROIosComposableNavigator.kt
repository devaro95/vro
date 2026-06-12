package com.vro.compose.navigator

import com.vro.navigation.VROBackResult
import com.vro.navigation.VRODestination
import com.vro.navigation.putResultParam
import com.vro.core_android.navigation.VRONavigator

/**
 * iOS Compose Multiplatform navigator.
 * Uses a simple screen-stack model since NavController is Android-only.
 */
class VROIosComposableNavigator<D : VRODestination>(
    private val onNavigate: (String) -> Unit,
    private val onBack: (VROBackResult?) -> Unit,
    private val onFinish: () -> Unit,
) : VRONavigator<D> {

    override fun onDestination(destination: D) {
        onNavigate(destination::class.simpleName ?: destination.toString())
    }

    override fun navigateBack(result: VROBackResult?) {
        result?.let { putResultParam(it.id, it) }
        onBack(result)
    }

    override fun finish() = onFinish()
}
