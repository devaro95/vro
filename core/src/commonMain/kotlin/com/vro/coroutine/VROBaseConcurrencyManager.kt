package com.vro.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

interface VROBaseConcurrencyManager {
    fun launch(
        dispatcher: CoroutineDispatcher = Dispatchers.Main,
        fullException: Boolean,
        block: suspend CoroutineScope.() -> Unit
    ): Job

    suspend fun cancelPendingTasks()

    suspend fun cancelTask(job: Job)
}