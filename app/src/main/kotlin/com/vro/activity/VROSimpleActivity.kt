package com.vro.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class VROSimpleActivity<VB : ViewBinding> : AppCompatActivity() {

    abstract fun createViewBinding(inflater: LayoutInflater): VB

    open lateinit var activityBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createViewBinding(layoutInflater).let {
            activityBinding = it
            setContentView(it.root)
        }
        onCreated(activityBinding)
    }

    @CallSuper
    abstract fun onCreated(binding: VB)

}