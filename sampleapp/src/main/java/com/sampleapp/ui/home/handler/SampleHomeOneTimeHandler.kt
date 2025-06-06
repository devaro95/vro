package com.sampleapp.ui.home.handler

import android.widget.Toast
import com.sampleapp.ui.home.*
import com.sampleapp.ui.home.SampleHomeViewModel.Companion.ONE_TIME_LAUNCH
import com.vro.compose.handler.VROOneTimeHandler

class SampleHomeOneTimeHandler : VROOneTimeHandler<SampleHomeState, SampleHomeEvents>() {

    override fun onOneTime(id: Int, state: SampleHomeState) {
        when (id) {
            ONE_TIME_LAUNCH -> Toast.makeText(context, "1000", Toast.LENGTH_LONG).show()
        }
    }
}