package com.sampleapp.ui.profile

import com.vro.navstarter.VRONavStarter

sealed class SampleProfileNavStarter : VRONavStarter {
    data class Initialize(val userId: String) : SampleProfileNavStarter()
}