package com.sampleapp

import android.app.Application
import com.sampleapp.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SampleApp : Application() {

    private val diModules = listOf(presentationModule)

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(applicationContext)
            modules(diModules)
        }
    }
}