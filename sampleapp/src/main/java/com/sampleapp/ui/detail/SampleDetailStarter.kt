package com.sampleapp.ui.detail

import com.vro.navstarter.VRONavStarter

sealed class SampleDetailStarter : VRONavStarter {
    data class Initial(val state: SampleDetailState) : SampleDetailStarter()
}