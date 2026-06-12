package com.vro.core_android.navigation

import com.vro.navigation.VROBackResult
import com.vro.navigation.VRODestination

/**
 * iOS stub navigator — will be implemented in Step 4 with the real iOS routing.
 */
class VROIosNavigator<D : VRODestination> : VRONavigator<D> {

    override fun onDestination(destination: D) {
        // TODO Step 4: implement iOS navigation
    }

    override fun navigateBack(result: VROBackResult?) {
        // TODO Step 4: implement iOS back navigation
    }

    override fun finish() {
        // TODO Step 4: implement iOS finish
    }
}
