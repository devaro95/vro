package com.vro.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import kotlin.coroutines.CoroutineContext

abstract class VROReceiver : BroadcastReceiver(), KoinComponent, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val job: Job = Job()

    abstract val intentList: List<String>

    override fun onReceive(context: Context, intent: Intent) {
        launch {
            if (intentList.isEmpty() || intentList.contains(intent.action)) {
                onReceive(intent)
            }
            job.cancel()
        }
    }

    abstract suspend fun onReceive(intent: Intent)
}
