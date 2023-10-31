package com.vro.testing

import com.vro.net.VROBaseConcurrencyManager
import com.vro.net.VROConcurrencyManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class VROTestDefaultConcurrencyManager : VROBaseConcurrencyManager {

    private val defaultConcurrencyManager = VROConcurrencyManager()
    override fun launch(dispatcher: CoroutineDispatcher, fullException: Boolean, block: suspend CoroutineScope.() -> Unit): Job {
        return defaultConcurrencyManager.launch(Dispatchers.Unconfined, fullException, block)
    }

    override fun cancelPendingTasks() {
        defaultConcurrencyManager.cancelPendingTasks()
    }

    override fun cancelTask(job: Job) {
        defaultConcurrencyManager.cancelTask(job)
    }
}