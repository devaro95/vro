package com.vro.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vro.fragment.VROViewModelFactory

abstract class VROInjectionActivity<VM : VROActivityViewModel> : AppCompatActivity() {

    abstract val viewModelSeed: VM

    val viewModel by lazy {
        ViewModelProvider(this, VROViewModelFactory(viewModelSeed))[viewModelSeed.javaClass]
    }

}