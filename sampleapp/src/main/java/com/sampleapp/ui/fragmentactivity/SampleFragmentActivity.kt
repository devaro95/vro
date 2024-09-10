package com.sampleapp.ui.fragmentactivity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import com.sampleapp.databinding.ActivityFragmentBinding
import com.sampleapp.ui.fragmentactivity.SampleFragmentNavigator.*
import com.vro.activity.VROActivity
import com.vro.state.VRODialogData
import org.koin.android.ext.android.inject

class SampleFragmentActivity :
    VROActivity<SampleFragmentState, ActivityFragmentBinding, SampleFragmentViewModel, SampleFragmentDestinations, SampleFragmentEvents>() {

    override fun createViewBinding(inflater: LayoutInflater) = ActivityFragmentBinding.inflate(inflater)

    override val viewModelSeed: SampleFragmentViewModel by inject()

    override val navigator: SampleFragmentNavigator by inject()

    override fun ActivityFragmentBinding.onError(error: Throwable) = Unit

    override fun onLoadDialog(data: VRODialogData) = Unit

    override fun onCreated(binding: ActivityFragmentBinding) = Unit

    override fun onViewUpdate(binding: ActivityFragmentBinding, data: SampleFragmentState) = Unit

    companion object {
        fun createIntent(context: Context) = Intent(context, SampleFragmentActivity::class.java)
    }
}